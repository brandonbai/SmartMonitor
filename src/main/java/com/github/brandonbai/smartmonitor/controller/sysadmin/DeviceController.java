package com.github.brandonbai.smartmonitor.controller.sysadmin;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
@Api(tags="设备管理")
public class DeviceController {
	
	@Resource
	private DeviceService deviceService;
	
	@RequestMapping(value = "control", method = RequestMethod.POST)
	@ApiOperation(value="控制设备",notes = "传输控制指令", response = Response.class)
	public Response deviceControl(String command) throws MsgException {
		if(StringUtils.isEmpty(command)) {
			throw new MsgException("命令为空");
		}
		deviceService.controlDevice(command);
		return Response.ok();
	}

	@RequestMapping("list/{areaId}")
	@ApiOperation(value="查看设备列表", notes = "根据区域id查询设备列表", response = Response.class)
	public Response deviceInfo(@PathVariable Integer areaId) {
		
		return Response.ok(deviceService.getDevices(areaId));
	}
	
	@RequestMapping("list")
	@ApiOperation(value="查询设备列表", response = Response.class)
	public Response deviceInfo(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<Device> pi = deviceService.getAllDevices(pageNum, pageSize);
		return Response.ok(pi);
	}
	
	@RequestMapping("commands/{deviceId}")
	@ApiOperation(value="查询指令列表", notes = "根据设备id查询指令列表", response = Response.class)
	public Response commands(@PathVariable Integer deviceId) {

		return Response.ok(deviceService.getCommands(deviceId));
	}

}
