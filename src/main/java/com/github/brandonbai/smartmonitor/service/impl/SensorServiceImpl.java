package com.github.brandonbai.smartmonitor.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.github.brandonbai.smartmonitor.config.MqttConfig;
import com.github.brandonbai.smartmonitor.dto.SensorDTO;
import com.github.brandonbai.smartmonitor.mapper.ThresholdMapper;
import com.github.brandonbai.smartmonitor.mqtt.MqttMessageConsumer;
import com.github.brandonbai.smartmonitor.mqtt.MqttMessageSender;
import com.github.brandonbai.smartmonitor.pojo.Log;
import com.github.brandonbai.smartmonitor.pojo.Threshold;
import com.github.brandonbai.smartmonitor.service.LogService;
import com.github.brandonbai.smartmonitor.service.RedisService;
import com.github.brandonbai.smartmonitor.vo.SensorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.stereotype.Service;

import com.github.brandonbai.smartmonitor.mapper.SensorMapper;
import com.github.brandonbai.smartmonitor.mapper.SensorValueMapper;
import com.github.brandonbai.smartmonitor.pojo.Sensor;
import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.brandonbai.smartmonitor.service.SensorService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * SensorServiceImpl 
 * @author brandonbai
 * @since 2016年10月19日
 *
 */
@Service
public class SensorServiceImpl implements SensorService {

	@Resource
	private SensorMapper sensorMapper;
	@Resource
	private SensorValueMapper sensorValueMapper;
	@Resource
	private MqttMessageSender mqttMessageSender;
	@Resource
	private RedisService redisService;
	@Resource
	private LogService logService;

	private MqttMessageConsumer mqttMessageConsumer;

	@Autowired
	public void setMqttMessageConsumer(MqttMessageConsumer mqttMessageConsumer) {
		this.mqttMessageConsumer = mqttMessageConsumer;
		this.mqttMessageConsumer.setSensorService(this);
	}

	@Override
	public List<SensorValue> findDataByTime(Integer sensorId, Date firstTime, Date lastTime) {
		if(sensorId != null) {
			if(firstTime==null || lastTime==null) {
				List<SensorValue> list = sensorValueMapper.findBySensorId(sensorId);
				if(list != null && list.size() > 1) {
					Collections.reverse(list);
				}
				return list;
			}
			return sensorValueMapper.findByTime(sensorId, firstTime, lastTime);
		}
		return null;
	}

	@Override
	public List<SensorVO> findSensorByAreaId(Integer areaId) {

		List<SensorVO> sensorList = sensorMapper.findSensorByAreaId(areaId);
		for (int i = 0; i < sensorList.size(); i++) {
			SensorVO sensor = sensorList.get(i);
			// 获取实时数值
			Double value = redisService.getRealValue(sensor.getId());
			sensor.setRealValue(value);
		}
		return sensorList;
	}

	@Override
	public PageInfo<SensorVO> findAllSensor(int pageNum, int pageSize) {
		List<SensorVO> sensorList = sensorMapper.findAllSensor(pageNum, pageSize);
		for (int i = 0; i < sensorList.size(); i++) {
			SensorVO sensor = sensorList.get(i);
			// 获取实时数值
			Double value = redisService.getRealValue(sensor.getId());
			sensor.setRealValue(value);
		}
		return new PageInfo<>(sensorList);
	}

	@Override
	public void addSensorValue(Integer sensorId, Double value) {
		SensorValue sensorValue = new SensorValue();
		sensorValue.setSensorId(sensorId);
        sensorValue.setTime(new Date());
        sensorValue.setValue(value);
		sensorValueMapper.addSensorValue(sensorValue);

		Threshold threshold = redisService.getThreshold(sensorId);
		if(threshold == null) {
			return;
		}
		Integer max = threshold.getMax();
		Integer min = threshold.getMin();
		Double realValue = redisService.getRealValue(sensorId);
		if(value < min || value > max) {
			if(realValue > min && realValue < max) {
				// 之前正常，现在异常 报警
				mqttMessageSender.sendMessage("sm/warn/"+sensorId, value);
				Log log = new Log();
				log.setType(Log.OUT_OF_THRESHOLD);
				log.setContent(String.format("节点[id=%d]数据异常，数值为%f", sensorId, value));
				log.setTime(new Date());
				logService.addLog(log);
			}
		} else {
			if(realValue < min || realValue > max) {
				// 之前异常，现在正常 通知
				mqttMessageSender.sendMessage("sm/normal/"+sensorId, value);
				Log log = new Log();
				log.setType(Log.OUT_OF_THRESHOLD);
				log.setContent(String.format("节点[id=%d]异常恢复，数值为%f", sensorId, value));
				log.setTime(new Date());
				logService.addLog(log);
			}
		}
		redisService.setRealValue(sensorId, value);
	}

	@Override
	public void addSensor(SensorDTO sensor) {
		sensorMapper.insertSensor(sensor);
	}

}
