package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Sensor;

/**
 * 
 * SensorMapper 
 * @author Feihu Ji
 * @since 2016年10月17日
 *
 */
public interface SensorMapper {

	List<Sensor> findSensorByAreaId(Integer areaId);

	List<Sensor> findAllSensor(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);
}
