package com.jifeihu.smartmonitor.service;

import java.util.List;

import com.jifeihu.smartmonitor.exception.MsgException;
import com.jifeihu.smartmonitor.pojo.Command;
import com.jifeihu.smartmonitor.pojo.Device;


public interface DeviceService {
	
	void controlDevice(String command) throws MsgException;
	
	void updateDeviceState(Integer id, Boolean state);

	List<Device> getDevices(Integer areaId);

	List<Device> getAllDevices(int pageNum, int pageSize);
	
	List<Command> getCommands(Integer deviceId);

	Device getDevice(Integer deviceId);

	Command getAutoCommand(Integer deviceId, String flag);

}
