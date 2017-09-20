package com.jifeihu.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jifeihu.smartmonitor.pojo.Sensor;

public interface SensorMapper {

	List<Sensor> findSensorByAreaId(Integer areaId);

	List<Sensor> findAllSensor(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);
}
