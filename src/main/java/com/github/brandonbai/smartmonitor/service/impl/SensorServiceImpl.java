package com.github.brandonbai.smartmonitor.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.github.brandonbai.smartmonitor.mqtt.MqttMessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Sensor> findSensorByAreaId(Integer areaId) {

		List<Sensor> sensorList = sensorMapper.findSensorByAreaId(areaId);
		for (int i = 0; i < sensorList.size(); i++) {
			Sensor sensor = sensorList.get(i);
			// 获取实时数值
			Integer value = mqttMessageConsumer.getValue(sensor.getId());
			sensor.setRealValue(value);
		}
		return sensorList;
	}

	@Override
	public PageInfo<Sensor> findAllSensor(int pageNum, int pageSize) {
		List<Sensor> sensorList = sensorMapper.findAllSensor(pageNum, pageSize);
		for (int i = 0; i < sensorList.size(); i++) {
			Sensor sensor = sensorList.get(i);
			// 获取实时数值
			Integer value = mqttMessageConsumer.getValue(sensor.getId());
			sensor.setRealValue(value);
		}
		return new PageInfo<>(sensorList);
	}

	@Override
	public void addSensorValue(Integer sensorId, Integer value) {
		SensorValue sensorValue = new SensorValue();
		sensorValue.setSensorId(sensorId);
        sensorValue.setTime(new Date());
        sensorValue.setValue(value);
		sensorValueMapper.addSensorValue(sensorValue);
		
	}

	@Override
	public void addSensor(Sensor sensor) {
		sensorMapper.insertSensor(sensor);
	}

}
