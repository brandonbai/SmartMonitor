package com.github.brandonbai.smartmonitor.controller.sysadmin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.pojo.DataType;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.DataTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * DataTypeController 
 * 数据类型处理
 * @author Feihu Ji
 * @since 2016年10月15日
 *
 */
@RestController
@RequestMapping("/datatype")
@Api(tags="数据类型管理")
public class DataTypeController {

	@Autowired
	private DataTypeService dataTypeService;
	
	@RequestMapping("/add")
	@ApiOperation(value="新增数据类型", response = Response.class)
	public Response dataTypeAdd(DataType dataType) {
		
		dataTypeService.add(dataType);
		
		return new Response().success();
	}
	
	@RequestMapping("/del")
	@ApiOperation(value="删除数据类型", response = Response.class)
	public Response dataTypeDel(Integer dataTypeId) {
		
		dataTypeService.delete(dataTypeId);
		
		return new Response().success();
	}
	
	@RequestMapping("/update")
	@ApiOperation(value="更新数据类型", response = Response.class)
	public Response dataTypeUpdate(DataType dataType) {
		
		dataTypeService.update(dataType);
		
		return new Response().success();
	}
	
	@RequestMapping("/one")
	@ApiOperation(value="查询单个数据类型", response = Response.class)
	public Response dataTypeOne(Integer dataTypeId) {
		
		dataTypeService.getOne(dataTypeId);
		
		return new Response().success();
	}
	@RequestMapping("/list")
	@ApiOperation(value="查询数据类型列表", response = Response.class)
	public Response dataTypeList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<DataType> pi = dataTypeService.getAll(pageNum, pageSize);
		
		return new Response().success(pi);
	}
	
	
}
