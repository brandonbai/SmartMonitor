package com.github.brandonbai.smartmonitor.service;

import java.util.Date;
import java.util.List;

import com.github.brandonbai.smartmonitor.pojo.Sensor;
import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.pagehelper.PageInfo;

public interface SensorService {
	
	List<SensorValue> findDataByTime(Integer sensorId, Date firstTime, Date lastTime);
	
	List<Sensor> findSensorByAreaId(Integer areaId);

	PageInfo<Sensor> findAllSensor(int pageNum, int pageSize);
	
	void addSensorValue(Integer sensorId, Integer value);

}
