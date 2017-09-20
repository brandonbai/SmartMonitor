package com.github.brandonbai.smartmonitor.service;

import com.github.brandonbai.smartmonitor.pojo.Area;
import com.github.pagehelper.PageInfo;

public interface AreaService {

	PageInfo<Area> getAll(int pageNum, int pageSize);
	
	Area getOne(Integer areaId);
	
	void update(Area area);
	
	void delete(Integer areaId);
	
	void add(Area area);
	
}
