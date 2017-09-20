package com.jifeihu.smartmonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jifeihu.smartmonitor.mapper.AreaMapper;
import com.jifeihu.smartmonitor.pojo.Area;
import com.jifeihu.smartmonitor.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaMapper areaMapper;
	
	public List<Area> getAll(int pageNum, int pageSize) {
		
		return areaMapper.findAll(pageNum, pageSize);
		
	}
	
	public Area getOne(Integer areaId) {
		
		
		return areaMapper.findOne(areaId);
		
	}
	
	public void update(Area area) {

		areaMapper.update(area);
		
	}
	
	public void delete(Integer areaId) {
		
		areaMapper.delete(areaId);
		
	}
	
	public void add(Area area) {
		
		areaMapper.add(area);
		
	}
	
}
