package com.jifeihu.smartmonitor.service;

import java.util.List;

import com.jifeihu.smartmonitor.pojo.Area;

public interface AreaService {

	List<Area> getAll(int pageNum, int pageSize);
	
	Area getOne(Integer areaId);
	
	void update(Area area);
	
	void delete(Integer areaId);
	
	void add(Area area);
	
}
