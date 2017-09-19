package com.jifeihu.smartmonitor.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jifeihu.smartmonitor.mapper.AreaMapper;
import com.jifeihu.smartmonitor.mapper.SensorMapper;
import com.jifeihu.smartmonitor.mapper.SensorValueMapper;
import com.jifeihu.smartmonitor.pojo.Sensor;
import com.jifeihu.smartmonitor.pojo.SensorValue;
import com.jifeihu.smartmonitor.service.SensorService;
import com.jifeihu.smartmonitor.websocket.MonitorWebSocketHandler;

@Service
public class SensorServiceImpl implements SensorService {
	@Resource
	private AreaMapper areaMapper;
	@Resource
	private SensorMapper sensorMapper;
	@Resource
	private SensorValueMapper sensorValueMapper;
	@Autowired
	private MonitorWebSocketHandler monitorWebSocketHandler;
	
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
			sensor.setRealValue(monitorWebSocketHandler.getRealTimeValue(sensor.getId()));
		}
		return sensorList;
	}

	@Override
	public List<Sensor> findAllSensor(int pageNum, int pageSize) {
		List<Sensor> sensorList = sensorMapper.findAllSensor(pageNum, pageSize);
		for (int i = 0; i < sensorList.size(); i++) {
			Sensor sensor = sensorList.get(i);
			sensor.setRealValue(monitorWebSocketHandler.getRealTimeValue(sensor.getId()));
		}
		return sensorList;
	}

	@Override
	public void addSensorValue(Integer sensorId, Integer value) {
		SensorValue sensorValue = new SensorValue();
		sensorValue.setSensorId(sensorId);
        sensorValue.setTime(new Date());
        sensorValue.setValue(value);
		sensorValueMapper.addSensorValue(sensorValue);
		
	}

}
