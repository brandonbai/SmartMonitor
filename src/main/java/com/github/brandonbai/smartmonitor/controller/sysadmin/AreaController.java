package com.github.brandonbai.smartmonitor.controller.sysadmin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.brandonbai.smartmonitor.pojo.Area;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.AreaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * AreaController 
 * 区域处理
 * @author brandonbai
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
	public Response areaAdd(@RequestBody Area area) {

		if(StringUtils.isEmpty(area.getName())) {
			return Response.err("名称不能为空");
		}

		areaService.add(area);
		
		return Response.ok();
	}
	
	@RequestMapping("/del/{areaId}")
	@ApiOperation(value="删除区域", response = Response.class)
	public Response areaDel(@PathVariable Integer areaId) {
		
		areaService.delete(areaId);
		
		return Response.ok();
	}
	
	@RequestMapping("/update")
	@ApiOperation(value="更新区域", response = Response.class)
	public Response areaUpdate(@RequestBody Area area) {
		
		areaService.update(area);
		
		return Response.ok();
	}
	
	@RequestMapping("/one/{areaId}")
	@ApiOperation(value="查询单个区域", response = Response.class)
	public Response areaOne(@PathVariable Integer areaId) {
		
		Area area = areaService.getOne(areaId);
		
		return Response.ok(area);
	}
	
	@RequestMapping("/list")
	@ApiOperation(value="查询区域列表吧", response = Response.class)
	public Response areaList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<Area> pi = areaService.getAll(pageNum, pageSize);
		
		return Response.ok(pi);
	}
}
