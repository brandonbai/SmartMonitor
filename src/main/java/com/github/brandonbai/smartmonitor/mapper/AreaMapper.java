package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Area;

/**
 * 
 * AreaMapper 
 * @author brandonbai
 * @since 2016年10月17日
 *
 */
public interface AreaMapper {
	
	List<Area> findAll(@Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize);

	Area findOne(Integer areaId);

	void update(Area area);

	void delete(Integer areaId);

	void add(Area area);

}
