package com.github.brandonbai.smartmonitor.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.SensorValue;

/**
 * 
 * SensorValueMapper 
 * @author brandonbai
 * @since 2016年10月17日
 *
 */
public interface SensorValueMapper {
	
	List<SensorValue> findBySensorId(Integer sensorId);
	
	List<SensorValue> findByTime(@Param("sId") Integer sId, @Param("ft") Date ft, @Param("lt") Date lt);

	void addSensorValue(SensorValue sensorValue);
}
