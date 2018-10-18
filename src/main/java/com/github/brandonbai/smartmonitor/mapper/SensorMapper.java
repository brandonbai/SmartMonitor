package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import com.github.brandonbai.smartmonitor.vo.SensorVO;
import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Sensor;

/**
 * 
 * SensorMapper 
 * @author brandonbai
 * @since 2016年10月17日
 *
 */
public interface SensorMapper {

	List<SensorVO> findSensorByAreaId(Integer areaId);

	List<SensorVO> findAllSensor(@Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize);

    void insertSensor(Sensor sensor);
}
