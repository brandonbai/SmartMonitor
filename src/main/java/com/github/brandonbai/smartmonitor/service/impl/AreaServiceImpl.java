package com.github.brandonbai.smartmonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.brandonbai.smartmonitor.mapper.AreaMapper;
import com.github.brandonbai.smartmonitor.pojo.Area;
import com.github.brandonbai.smartmonitor.service.AreaService;
import com.github.pagehelper.PageInfo;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaMapper areaMapper;
	
	@Override
	public PageInfo<Area> getAll(int pageNum, int pageSize) {
		
		List<Area> list = areaMapper.findAll(pageNum, pageSize);
		
		return new PageInfo<Area>(list);
		
	}
	
	@Override
	public Area getOne(Integer areaId) {
		
		
		return areaMapper.findOne(areaId);
		
	}
	
	@Override
	public void update(Area area) {

		areaMapper.update(area);
		
	}
	
	@Override
	public void delete(Integer areaId) {
		
		areaMapper.delete(areaId);
		
	}
	
	@Override
	public void add(Area area) {
		
		areaMapper.add(area);
		
	}
	
}
