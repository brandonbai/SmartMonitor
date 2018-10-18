package com.github.brandonbai.smartmonitor.pojo;

import java.util.Date;

/**
 * 
 * SensorValue 
 * 传感器采集数值
 * @author brandonbai
 * @since 2016年10月11日
 *
 */
public class SensorValue {
	private Integer sensorId;
	private String sensorName;
	private Date time;
	private Double value;

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
