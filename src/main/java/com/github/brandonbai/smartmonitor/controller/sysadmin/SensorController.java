package com.github.brandonbai.smartmonitor.controller.sysadmin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.Sensor;
import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.brandonbai.smartmonitor.service.SensorService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/sysinfo/")
public class SensorController {
	@Resource
	private SensorService sensorService;
	
	@RequestMapping("sensor/{areaId}")
	public Response sensorInfo(@PathVariable Integer areaId) {
		return new Response().success(sensorService.findSensorByAreaId(areaId));
	}
	
	@RequestMapping("sensor/list")
	public Response sensorList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<Sensor> pi = sensorService.findAllSensor(pageNum, pageSize);
		
		return new Response().success(pi);
	}
	
	@RequestMapping("sensorValue/{sensorId}")
	public Response sensorValue(@PathVariable Integer sensorId, Date ft, Date lt) {
		List<SensorValue> svList = sensorService.findDataByTime(sensorId, ft, lt);
		return new Response().success(svList);
	}

}
