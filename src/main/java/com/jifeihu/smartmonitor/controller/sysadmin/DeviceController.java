package com.jifeihu.smartmonitor.controller.sysadmin;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jifeihu.smartmonitor.annotation.RolePermission;
import com.jifeihu.smartmonitor.annotation.RoleType;
import com.jifeihu.smartmonitor.exception.MsgException;
import com.jifeihu.smartmonitor.pojo.Response;
import com.jifeihu.smartmonitor.service.DeviceService;
import com.jifeihu.smartmonitor.utils.TextUtils;

@RestController
@RequestMapping("/device/")
public class DeviceController {
	
	@Resource
	private DeviceService deviceService;
	
	@RolePermission(RoleType.ROLE_ADMIN)
	@RequestMapping(value = "control", method = RequestMethod.POST)
	public Response deviceControl(String command) throws MsgException {
		if(TextUtils.isEmpty(command)) {
			throw new MsgException("命令为空");
		}
		deviceService.controlDevice(command);
		return new Response().success();
	}

	@RequestMapping("list/{areaId}")
	public Response deviceInfo(@PathVariable Integer areaId) {
		
		return new Response().success(deviceService.getDevices(areaId));
	}
	
	@RequestMapping("list")
	public Response deviceInfo(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		return new Response().success(deviceService.getAllDevices(pageNum, pageSize));
	}
	
	@RequestMapping("commands/{deviceId}")
	public Response commands(@PathVariable Integer deviceId) {

		return new Response().success(deviceService.getCommands(deviceId));
	}

}
