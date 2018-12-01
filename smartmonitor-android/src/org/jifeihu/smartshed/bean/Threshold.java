package org.jifeihu.smartshed.bean;

/**
 * ��ֵ��Ϣ
 */
public class Threshold {
	// ��������ʶ
	private int sensorID;
	// ��������
	private String name;
	// ���ֵ
	private int max;
	// ��Сֵ
	private int min;
	
	public Threshold(){}
	
	public Threshold(int sensorID, String name, int max, int min) {
		super();
		this.sensorID = sensorID;
		this.name = name;
		this.max = max;
		this.min = min;
	}
	
	public int getSensorID() {
		return sensorID;
	}
	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}

	@Override
	public String toString() {
		return "Threshold [sensorID=" + sensorID + ", name=" + name + ", max="
				+ max + ", min=" + min + "]";
	}
	
}
