package com.jifeihu.smartmonitor.service;

import java.util.Date;
import java.util.List;

import com.jifeihu.smartmonitor.pojo.Sensor;
import com.jifeihu.smartmonitor.pojo.SensorValue;

public interface SensorService {
	
	List<SensorValue> findDataByTime(Integer sensorId, Date firstTime, Date lastTime);
	
	List<Sensor> findSensorByAreaId(Integer areaId);

	List<Sensor> findAllSensor(int pageNum, int pageSize);
	
	void addSensorValue(Integer sensorId, Integer value);

}
