package com.github.brandonbai.smartmonitor.controller;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.Threshold;
import com.github.brandonbai.smartmonitor.service.ThresholdService;

/**
 * 
 * ThresholdController 
 * 阈值处理
 * @author brandonbai
 * @since 2016年10月15日
 *
 */
@RestController
@RequestMapping("/threshold")
@CrossOrigin
@Api(tags="区阈值理")
public class ThresholdController {
	
	@Resource
	private ThresholdService thresholdService;
	
	@GetMapping("/info/{sensorId}")
	@ApiOperation(value="查询阈值", notes = "根据传感器id查询阈值",response = Response.class)
	public Response thresholdInfo(@PathVariable Integer sensorId) {
		return Response.ok(thresholdService.findOne(sensorId));
	}

	@PostMapping(value = "/update")
	@ApiOperation(value="修改阈值", response = Response.class)
	public Response updateThreshold(@RequestBody Threshold threshold) {
		thresholdService.updateThreshold(threshold);
		return Response.ok(thresholdService.findOne(threshold.getId()));
	}

	@PostMapping(value = "/add")
	@ApiOperation(value="新增阈值", response = Response.class)
	public Response addThreshold(@RequestBody Threshold threshold) {
		thresholdService.insertThreshold(threshold);
		return Response.ok();
	}

}
