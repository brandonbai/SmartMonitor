package com.github.brandonbai.smartmonitor.service;

import com.github.pagehelper.PageInfo;
import com.jifeihu.smartmonitor.pojo.Area;

public interface AreaService {

	PageInfo<Area> getAll(int pageNum, int pageSize);
	
	Area getOne(Integer areaId);
	
	void update(Area area);
	
	void delete(Integer areaId);
	
	void add(Area area);
	
}
