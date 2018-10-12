package com.github.brandonbai.smartmonitor.service;

import java.util.Date;
import java.util.List;

import com.github.brandonbai.smartmonitor.pojo.Sensor;
import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.pagehelper.PageInfo;

/**
 * 
 * SensorService 
 * @author Feihu Ji
 * @since 2016年10月19日
 *
 */
public interface SensorService {
	
	List<SensorValue> findDataByTime(Integer sensorId, Date firstTime, Date lastTime);
	
	List<Sensor> findSensorByAreaId(Integer areaId);

	PageInfo<Sensor> findAllSensor(int pageNum, int pageSize);

	/**
	 * 添加传感器数值
	 * @param sensorId
	 * @param value
	 */
	void addSensorValue(Integer sensorId, Integer value);

	/**
	 * 添加传感器
	 * @param sensor
	 */
    void addSensor(Sensor sensor);
}
