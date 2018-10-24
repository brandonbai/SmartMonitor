package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Area;
import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * AreaMapper 
 * @author brandonbai
 * @since 2016年10月17日
 *
 */
public interface AreaMapper extends Mapper<Area> {
	
	List<Area> findAll(@Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize);

	Area findOne(Integer areaId);

	void updateArea(Area area);

	void deleteArea(Integer areaId);

	void addArea(Area area);

}
