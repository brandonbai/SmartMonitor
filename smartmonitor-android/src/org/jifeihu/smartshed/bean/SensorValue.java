package org.jifeihu.smartshed.bean;

/**
 * 参数值信息
 */
public class SensorValue {
	// 数值
	private int value;
	// 传感器编号
	private int sensorId;
	public SensorValue() {}
	
	public SensorValue(int value, int sensorId) {
		super();
		this.value = value;
		this.sensorId = sensorId;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getSensorId() {
		return sensorId;
	}
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	@Override
	public String toString() {
		return "SensorValue [value=" + value + ", sensorId=" + sensorId + "]";
	}
	
}
