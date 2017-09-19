package com.jifeihu.smartmonitor.pojo;

import java.util.Date;

public class SensorValue {
	private Integer sensorId;
	private String SensorName;
	private Date time;
	private Integer value;

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public String getSensorName() {
		return SensorName;
	}
	public void setSensorName(String sensorName) {
		SensorName = sensorName;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
