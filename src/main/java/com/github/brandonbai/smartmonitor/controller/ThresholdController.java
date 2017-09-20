package com.github.brandonbai.smartmonitor.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.annotation.RolePermission;
import com.github.brandonbai.smartmonitor.annotation.RoleType;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.Threshold;
import com.github.brandonbai.smartmonitor.service.ThresholdService;

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
