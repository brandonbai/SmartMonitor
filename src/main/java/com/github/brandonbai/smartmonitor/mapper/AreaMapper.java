package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jifeihu.smartmonitor.pojo.Area;

public interface AreaMapper {
	
	public List<Area> findAll(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);

	public Area findOne(Integer areaId);

	public void update(Area area);

	public void delete(Integer areaId);

	public void add(Area area);

}
