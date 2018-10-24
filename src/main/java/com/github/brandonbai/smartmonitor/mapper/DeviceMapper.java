package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Command;
import com.github.brandonbai.smartmonitor.pojo.Device;
import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * DeviceMapper 
 * @author brandonbai
 * @since 2016年10月17日
 *
 */
public interface DeviceMapper extends Mapper<Device> {

	void updateDeviceState(@Param("id") Integer id, @Param("state") Boolean state);

	List<Device> findDevice(Integer areaId);

	List<Command> getCommands(Integer deviceId);

	Device getDevice(Integer deviceId);

	Command getAutoCommand(@Param("deviceId") Integer deviceId, @Param("flag") String flag);

	List<Device> getAllDevices(@Param("pageNum") int pageNum,
							   @Param("pageSize") int pageSize);

	Device findDeviceByCommand(String command);

	String getFlagByCommand(String command);
}
