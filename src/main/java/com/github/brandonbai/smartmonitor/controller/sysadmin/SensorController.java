package com.github.brandonbai.smartmonitor.controller.sysadmin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.Sensor;
import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.brandonbai.smartmonitor.service.SensorService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * SensorController 
 * 传感器相关
 * @author Feihu Ji
 * @since 2016年10月15日
 *
 */
@RestController
@RequestMapping("/sysinfo/")
@Api(tags="传感器管理")
public class SensorController {
	@Resource
	private SensorService sensorService;
	
	@RequestMapping("sensor/{areaId}")
	@ApiOperation(value="查询传感器列表", notes = "根据区域id查询传感器列表", response = Response.class)
	public Response sensorInfo(@PathVariable Integer areaId) {
		return new Response().success(sensorService.findSensorByAreaId(areaId));
	}
	
	@RequestMapping("sensor/list")
	@ApiOperation(value="查询传感器列表", notes = "分页查询", response = Response.class)
	public Response sensorList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<Sensor> pi = sensorService.findAllSensor(pageNum, pageSize);
		
		return new Response().success(pi);
	}
	
	@RequestMapping("sensorValue/{sensorId}")
	@ApiOperation(value="查询传感器数值", notes = "根据传感器id和时间段查询",response = Response.class)
	public Response sensorValue(@PathVariable Integer sensorId, Date ft, Date lt) {
		List<SensorValue> svList = sensorService.findDataByTime(sensorId, ft, lt);
		return new Response().success(svList);
	}

}
