package org.jifeihu.smartshed.bean;

/**
 * ����ֵ��Ϣ
 */
public class SensorValue {
	// ��ֵ
	private int value;
	// ���������
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
