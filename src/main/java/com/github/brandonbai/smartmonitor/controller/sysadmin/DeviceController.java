package com.github.brandonbai.smartmonitor.controller.sysadmin;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.annotation.RolePermission;
import com.github.brandonbai.smartmonitor.annotation.RoleType;
import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.Device;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.DeviceService;
import com.github.brandonbai.smartmonitor.utils.TextUtils;
import com.github.pagehelper.PageInfo;

/**
 * 
 * DeviceController 
 * 设备相关
 * @author Feihu Ji
 * @since 2016年10月15日
 *
 */
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
		
		PageInfo<Device> pi = deviceService.getAllDevices(pageNum, pageSize);
		return new Response().success(pi);
	}
	
	@RequestMapping("commands/{deviceId}")
	public Response commands(@PathVariable Integer deviceId) {

		return new Response().success(deviceService.getCommands(deviceId));
	}

}
