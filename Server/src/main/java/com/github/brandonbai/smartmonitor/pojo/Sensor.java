package com.github.brandonbai.smartmonitor.pojo;

/**
 * 
 * Sensor 
 * 传感器
 * @author brandonbai
 * @since 2016年10月11日
 *
 */
public class Sensor extends BaseDevice {
	/** 单位 */
	private String unit;
	/** 阈值id*/
	private Integer thresholdId;
	/** 设备id*/
	private Integer deviceId;
	/** 节点id*/
	private Integer nodeId;

	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getThresholdId() {
		return thresholdId;
	}

	public void setThresholdId(Integer thresholdId) {
		this.thresholdId = thresholdId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
}
