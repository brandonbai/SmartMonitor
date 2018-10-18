package com.github.brandonbai.smartmonitor.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.mapper.DeviceMapper;
import com.github.brandonbai.smartmonitor.pojo.Command;
import com.github.brandonbai.smartmonitor.pojo.Device;
import com.github.brandonbai.smartmonitor.service.DeviceService;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

/**
 * 
 * DeviceServiceImpl 
 * @author brandonbai
 * @since 2016年10月19日
 *
 */
@Service
public class DeviceServiceImpl implements DeviceService {
	
	@Resource
	private DeviceMapper deviceMapper;

	/** 开标识*/
	private static final String FLAG_ON = "on";
	/** 关标识*/
	private static final String FLAG_OFF = "off";
	
	/**
	 * 设备控制
	 * @param command 命令
	 * @return 下位机返回的结果
	 */
	@Override
	public void controlDevice(String command) throws MsgException {
		Device device = deviceMapper.findDeviceByCommand(command);
		if(device != null) {
			String flag = deviceMapper.getFlagByCommand(command);
			if(FLAG_ON.equals(flag)) {
				if(!device.getState()) {
//					if(!monitorWebSocketHandler.sendMessage(command)) {
//						throw new MsgException("发送指令失败，请检查下位机连接状况");
//					}
					updateDeviceState(device.getId(), true);
				}else {
					throw new MsgException("设备已经开启，请勿重复操作");
				}
			} else if(FLAG_OFF.equals(flag)) {
				if(device.getState()) {
//					if(!monitorWebSocketHandler.sendMessage(command)) {
//						throw new MsgException("发送指令失败，请检查下位机连接状况");
//					}
					updateDeviceState(device.getId(), false);
				}else {
					throw new MsgException("设备已经关闭，请勿重复操作");
				}
			} else {
//				if(!monitorWebSocketHandler.sendMessage(command)) {
//					throw new MsgException("发送指令失败，请检查下位机连接状况");
//				}
			}
			
		}
		
	}

	@Override
	public void updateDeviceState(Integer id, Boolean state) {
		deviceMapper.updateDeviceState(id, state);
		
	}

	@Override
	public List<Device> getDevices(Integer areaId) {
		return deviceMapper.findDevice(areaId);
	}

	@Override
	public List<Command> getCommands(Integer deviceId) {
		return deviceMapper.getCommands(deviceId);
	}

	@Override
	public Device getDevice(Integer deviceId) {
		return deviceMapper.getDevice(deviceId);
	}

	@Override
	public Command getAutoCommand(Integer deviceId, String flag) {
		return deviceMapper.getAutoCommand(deviceId, flag);
	}

	@Override
	public PageInfo<Device> getAllDevices(int pageNum, int pageSize) {
		
		List<Device> list = deviceMapper.getAllDevices(pageNum, pageSize);
		
		return new PageInfo<Device>(list);
	}
}
