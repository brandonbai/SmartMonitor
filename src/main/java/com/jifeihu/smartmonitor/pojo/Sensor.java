package com.jifeihu.smartmonitor.pojo;

/**
 * 传感器
 */
public class Sensor extends BaseDevice {
	// 单位
	private String unit;
	// 实时值
	private Integer realValue;
	// 阈值
	private Threshold threshold;
	// 设备id
	private Integer deviceId;

	public Integer getRealValue() {
		return realValue;
	}

	public void setRealValue(Integer realValue) {
		this.realValue = realValue;
	}

	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Threshold getThreshold() {
		return threshold;
	}

	public void setThreshold(Threshold threshold) {
		this.threshold = threshold;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	
}
