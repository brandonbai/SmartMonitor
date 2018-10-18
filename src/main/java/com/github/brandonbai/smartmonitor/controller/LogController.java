package com.github.brandonbai.smartmonitor.controller;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.LogService;

/**
 * 
 * LogController 
 * 日志处理
 * @author brandonbai
 * @since 2016年10月15日
 */
@RestController
@RequestMapping("/log")
@CrossOrigin
@Api(tags="日志管理")
public class LogController {
	@Resource
	private LogService logService;
	
	@RequestMapping("/list")
	@ApiOperation(value="查询日志列表", notes = "分页查询",response = Response.class)
	public Response logInfo(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "0")Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return  Response.ok(logService.getLog());
	}

	@RequestMapping("/list/{date}")
	@ApiOperation(value="查询日志列表", notes = "根据日期查询",response = Response.class)
	public Response logInfo(@PathVariable String date) {
		return Response.ok(logService.getLog(date));
	}
	
}
