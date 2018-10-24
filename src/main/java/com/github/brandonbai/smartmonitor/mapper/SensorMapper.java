package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import com.github.brandonbai.smartmonitor.vo.SensorVO;
import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Sensor;
import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * SensorMapper 
 * @author brandonbai
 * @since 2016年10月17日
 *
 */
public interface SensorMapper extends Mapper<Sensor> {

	List<SensorVO> findSensorByAreaId(Integer areaId);

	List<SensorVO> findAllSensor(@Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize);

    void insertSensor(Sensor sensor);
}
