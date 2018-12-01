package org.jifeihu.smartshed.dao;

import java.util.List;
import java.util.Map;

import org.jifeihu.smartshed.bean.Device;
import org.jifeihu.smartshed.bean.SensorValue;
import org.jifeihu.smartshed.bean.Threshold;


public interface Dao {
	
	// 添加日志信息
	void insertLog(String content);
	// 添加环境数据
	void insertData(SensorValue sensorValue);
	// 获取阈值
	List<Threshold> getThresholds();
	// 修改设备状态
	void setDeviceState(int sensorId, boolean state);
	// 获取设备信息
	Map<Integer,Device> getDeviceInfo();
}
