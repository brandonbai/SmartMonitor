package com.github.brandonbai.smartmonitor.controller.sysadmin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
 * 区域处理
 * @author Feihu Ji
 * @since 2016年10月15日
 *
 */
@RestController
@RequestMapping("/area")
@CrossOrigin
@Api(tags="区域管理")
public class AreaController {
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/add")
	@ApiOperation(value="新增区域", response = Response.class)
	public Response areaAdd(Area area) {
		
		areaService.add(area);
		
		return new Response().success();
	}
	
	@RequestMapping("/del")
	@ApiOperation(value="删除区域", response = Response.class)
	public Response areaDel(Integer areaId) {
		
		areaService.delete(areaId);
		
		return new Response().success();
	}
	
	@RequestMapping("/update")
	@ApiOperation(value="更新区域", response = Response.class)
	public Response areaUpdate(Area area) {
		
		areaService.update(area);
		
		return new Response().success();
	}
	
	@RequestMapping("/one")
	@ApiOperation(value="查询单个区域", response = Response.class)
	public Response areaOne(Integer areaId) {
		
		Area area = areaService.getOne(areaId);
		
		return new Response().success(area);
	}
	
	@RequestMapping("/list")
	@ApiOperation(value="查询区域列表吧", response = Response.class)
	public Response areaList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<Area> pi = areaService.getAll(pageNum, pageSize);
		
		return new Response().success(pi);
	}
}
