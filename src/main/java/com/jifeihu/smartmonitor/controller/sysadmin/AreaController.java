package com.jifeihu.smartmonitor.controller.sysadmin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jifeihu.smartmonitor.pojo.Area;
import com.jifeihu.smartmonitor.pojo.Response;
import com.jifeihu.smartmonitor.service.AreaService;

@RestController
@RequestMapping("/area")
public class AreaController {
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/add")
	public Response areaAdd(Area area) {
		
		areaService.add(area);
		
		return new Response().success();
	}
	
	@RequestMapping("/del")
	public Response areaDel(Integer areaId) {
		
		areaService.delete(areaId);
		
		return new Response().success();
	}
	
	@RequestMapping("/update")
	public Response areaUpdate(Area area) {
		
		areaService.update(area);
		
		return new Response().success();
	}
	
	@RequestMapping("/one")
	public Response areaOne(Integer areaId) {
		
		Area area = areaService.getOne(areaId);
		
		return new Response().success(area);
	}
	
	@RequestMapping("/list")
	public Response areaList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		List<Area> areaList = areaService.getAll(pageNum, pageSize);
		
		return new Response().success(areaList);
	}
}
