package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Area;

/**
 * 
 * AreaMapper 
 * @author Feihu Ji
 * @sine 2016年10月17日
 *
 */
public interface AreaMapper {
	
	public List<Area> findAll(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);

	public Area findOne(Integer areaId);

	public void update(Area area);

	public void delete(Integer areaId);

	public void add(Area area);

}
