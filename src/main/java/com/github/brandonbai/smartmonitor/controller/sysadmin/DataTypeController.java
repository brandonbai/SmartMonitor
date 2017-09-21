package com.github.brandonbai.smartmonitor.controller.sysadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.pojo.DataType;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.DataTypeService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/datatype")
public class DataTypeController {

	@Autowired
	private DataTypeService dataTypeService;
	
	@RequestMapping("/add")
	public Response dataTypeAdd(DataType dataType) {
		
		dataTypeService.add(dataType);
		
		return new Response().success();
	}
	
	@RequestMapping("/del")
	public Response dataTypeDel(Integer dataTypeId) {
		
		dataTypeService.delete(dataTypeId);
		
		return new Response().success();
	}
	
	@RequestMapping("/update")
	public Response dataTypeUpdate(DataType dataType) {
		
		dataTypeService.update(dataType);
		
		return new Response().success();
	}
	
	@RequestMapping("/one")
	public Response dataTypeOne(Integer dataTypeId) {
		
		dataTypeService.getOne(dataTypeId);
		
		return new Response().success();
	}
	@RequestMapping("/list")
	public Response dataTypeList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<DataType> pi = dataTypeService.getAll(pageNum, pageSize);
		
		return new Response().success(pi);
	}
	
	
}
