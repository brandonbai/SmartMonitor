package com.jifeihu.smartmonitor.controller.sysadmin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jifeihu.smartmonitor.pojo.Response;
import com.jifeihu.smartmonitor.pojo.SensorValue;
import com.jifeihu.smartmonitor.service.SensorService;

@RestController
@RequestMapping("/sensor")
public class SensorController {
	@Resource
	private SensorService sensorService;
	
	@RequestMapping("/{areaId}")
	public Response sensorInfo(@PathVariable Integer areaId) {
		return new Response().success(sensorService.findSensorByAreaId(areaId));
	}
	
	@RequestMapping("/list")
	public Response sensorList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		return new Response().success(sensorService.findAllSensor(pageNum, pageSize));
	}
	
	@RequestMapping("sensorValue/{sensorId}")
	public Response sensorValue(@PathVariable Integer sensorId, Date ft, Date lt) {
		List<SensorValue> svList = sensorService.findDataByTime(sensorId, ft, lt);
		return new Response().success(svList);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
