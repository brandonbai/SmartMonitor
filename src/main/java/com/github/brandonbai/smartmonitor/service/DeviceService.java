package com.github.brandonbai.smartmonitor.service;

import java.util.List;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.Command;
import com.github.brandonbai.smartmonitor.pojo.Device;
import com.github.pagehelper.PageInfo;

/**
 * 
 * DeviceService 
 * @author Feihu Ji
 * @sine 2016年10月19日
 *
 */
public interface DeviceService {
	
	void controlDevice(String command) throws MsgException;
	
	void updateDeviceState(Integer id, Boolean state);

	List<Device> getDevices(Integer areaId);

	PageInfo<Device> getAllDevices(int pageNum, int pageSize);
	
	List<Command> getCommands(Integer deviceId);

	Device getDevice(Integer deviceId);

	Command getAutoCommand(Integer deviceId, String flag);

}
