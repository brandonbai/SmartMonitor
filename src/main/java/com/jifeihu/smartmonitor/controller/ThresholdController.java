package com.jifeihu.smartmonitor.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jifeihu.smartmonitor.annotation.RolePermission;
import com.jifeihu.smartmonitor.annotation.RoleType;
import com.jifeihu.smartmonitor.pojo.Response;
import com.jifeihu.smartmonitor.pojo.Threshold;
import com.jifeihu.smartmonitor.service.ThresholdService;

@RestController
@RequestMapping("/threshold/")
public class ThresholdController {
	
	@Resource
	private ThresholdService thresholdService;
	
	@RequestMapping("{sensorId}")
	public Response thresholdInfo(@PathVariable Integer sensorId) {
		return new Response().success(thresholdService.findOne(sensorId));
	}

	@RolePermission(RoleType.ROLE_ADMIN)
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Response updateThreshold(Threshold threshold) {
		thresholdService.updateThresholds(threshold);
		return new Response().success(thresholdService.findOne(threshold.getId()));
	}

}
