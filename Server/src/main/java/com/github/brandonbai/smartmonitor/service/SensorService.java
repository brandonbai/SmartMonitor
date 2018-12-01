package com.github.brandonbai.smartmonitor.service;

import java.util.Date;
import java.util.List;

import com.github.brandonbai.smartmonitor.dto.SensorDTO;
import com.github.brandonbai.smartmonitor.pojo.Sensor;
import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.brandonbai.smartmonitor.vo.SensorVO;
import com.github.pagehelper.PageInfo;

/**
 * 
 * SensorService 
 * @author brandonbai
 * @since 2016年10月19日
 *
 */
public interface SensorService {
	
	List<SensorValue> findDataByTime(Integer sensorId, Date firstTime, Date lastTime);
	
	List<SensorVO> findSensorByAreaId(Integer areaId);

	PageInfo<SensorVO> findAllSensor(int pageNum, int pageSize);

	/**
	 * 添加传感器数值
	 * @param sensorId
	 * @param value
	 */
	void addSensorValue(Integer sensorId, Double value);

	/**
	 * 添加传感器
	 * @param sensor
	 */
    void addSensor(SensorDTO sensor);
}
