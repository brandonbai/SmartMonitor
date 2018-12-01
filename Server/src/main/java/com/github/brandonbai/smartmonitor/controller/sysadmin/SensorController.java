package com.github.brandonbai.smartmonitor.controller.sysadmin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.github.brandonbai.smartmonitor.dto.SensorDTO;
import com.github.brandonbai.smartmonitor.vo.SensorVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.brandonbai.smartmonitor.service.SensorService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * SensorController 
 * 传感器相关
 * @author brandonbai
 * @since 2016年10月15日
 *
 */
@RestController
@RequestMapping("/sysinfo/")
@CrossOrigin
@Api(tags="传感器管理")
public class SensorController {
	@Resource
	private SensorService sensorService;
	
	@RequestMapping("sensor/info/{areaId}")
	@ApiOperation(value="查询传感器列表", notes = "根据区域id查询传感器列表", response = Response.class)
	public Response sensorInfo(@PathVariable Integer areaId) {
		return new Response().success(sensorService.findSensorByAreaId(areaId));
	}

	@RequestMapping("sensor/add")
	@ApiOperation(value="查询传感器列表", notes = "根据区域id查询传感器列表", response = Response.class)
	public Response sensorInfo(@RequestBody SensorDTO sensor) {

		if(sensor == null) {
			return Response.err("名称不能为空");
		}
		if(StringUtils.isBlank(sensor.getName())) {
			return Response.err("名称不能为空");
		}
		if(StringUtils.isBlank(sensor.getUnit())) {
			return Response.err("单位不能为空");
		}
		if(sensor.getNodeId() == null || sensor.getDeviceId() == null) {
			return Response.err("设备/节点不能为空");
		}
		if(sensor.getThresholdId() == null) {
			return Response.err("阈值不能为空");
		}
		sensorService.addSensor(sensor);
		return Response.ok();
	}
	
	@RequestMapping("sensor/list")
	@ApiOperation(value="查询传感器列表", notes = "分页查询", response = Response.class)
	public Response sensorList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<SensorVO> pi = sensorService.findAllSensor(pageNum, pageSize);
		
		return Response.ok(pi);
	}
	
	@RequestMapping("sensorValue/{sensorId}")
	@ApiOperation(value="查询传感器数值", notes = "根据传感器id和时间段查询",response = Response.class)
	public Response sensorValue(@PathVariable Integer sensorId, Date ft, Date lt) {
		List<SensorValue> svList = sensorService.findDataByTime(sensorId, ft, lt);
		return Response.ok(svList);
	}

}
