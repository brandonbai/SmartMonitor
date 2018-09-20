package com.github.brandonbai.smartmonitor.controller;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.annotation.RolePermission;
import com.github.brandonbai.smartmonitor.annotation.RoleType;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.Threshold;
import com.github.brandonbai.smartmonitor.service.ThresholdService;

/**
 * 
 * ThresholdController 
 * 阈值处理
 * @author Feihu Ji
 * @since 2016年10月15日
 *
 */
@RestController
@RequestMapping("/threshold/")
@Api(tags="区阈值理")
public class ThresholdController {
	
	@Resource
	private ThresholdService thresholdService;
	
	@RequestMapping("{sensorId}")
	@ApiOperation(value="查询阈值", notes = "根据传感器id查询阈值",response = Response.class)
	public Response thresholdInfo(@PathVariable Integer sensorId) {
		return new Response().success(thresholdService.findOne(sensorId));
	}

	@RolePermission(RoleType.ROLE_ADMIN)
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ApiOperation(value="修改阈值", response = Response.class)
	public Response updateThreshold(Threshold threshold) {
		thresholdService.updateThresholds(threshold);
		return new Response().success(thresholdService.findOne(threshold.getId()));
	}

}
