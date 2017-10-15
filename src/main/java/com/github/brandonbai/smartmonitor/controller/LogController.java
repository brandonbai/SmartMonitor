package com.github.brandonbai.smartmonitor.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.LogService;

/**
 * 
 * LogController 
 * @Description: 日志处理
 * @author Feihu Ji
 * @sine 2016年10月15日
 *
 */
@RestController
@RequestMapping("/log")
public class LogController {
	@Resource
	private LogService logService;
	
	@RequestMapping("/list")
	public Response logInfo() {
		return  new Response().success(logService.getLog());
	}

	@RequestMapping("/list/{date}")
	public Response logInfo(@PathVariable String date) {
		return new Response().success(logService.getLog(date));
	}
	
}
