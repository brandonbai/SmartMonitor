package com.github.brandonbai.smartmonitor.pojo;

/**
 * 
 * Sensor 
 * 传感器
 * @author Feihu Ji
 * @since 2016年10月11日
 *
 */
public class Sensor extends BaseDevice {
	/** 单位 */
	private String unit;
	/** 实时值*/
	private Integer realValue;
	/** 阈值*/
	private Threshold threshold;
	/** 设备id*/
	private Integer deviceId;
	/** 节点id*/
	private Integer nodeId;

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

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
}
