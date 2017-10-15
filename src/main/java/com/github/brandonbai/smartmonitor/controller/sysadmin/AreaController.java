package com.github.brandonbai.smartmonitor.controller.sysadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.pojo.Area;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.AreaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * AreaController 
 * @Description: 区域处理
 * @author Feihu Ji
 * @sine 2016年10月15日
 *
 */
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
		
		PageInfo<Area> pi = areaService.getAll(pageNum, pageSize);
		
		return new Response().success(pi);
	}
}
