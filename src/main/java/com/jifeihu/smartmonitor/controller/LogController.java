package com.jifeihu.smartmonitor.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jifeihu.smartmonitor.pojo.Response;
import com.jifeihu.smartmonitor.service.LogService;

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
