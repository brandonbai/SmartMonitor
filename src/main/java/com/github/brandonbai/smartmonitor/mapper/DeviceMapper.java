package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jifeihu.smartmonitor.pojo.Command;
import com.jifeihu.smartmonitor.pojo.Device;

public interface DeviceMapper {
	
	public void updateDeviceState(@Param("id") Integer id, @Param("state") Boolean state);
	
	public List<Device> findDevice(Integer areaId);

	public List<Command> getCommands(Integer deviceId);

	public Device getDevice(Integer deviceId);

	public Command getAutoCommand(@Param("deviceId") Integer deviceId, @Param("flag") String flag);

	public List<Device> getAllDevices(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);

	public Device findDeviceByCommand(String command);

	public String getFlagByCommand(String command);
}
