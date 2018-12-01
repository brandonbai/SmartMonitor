package org.jifeihu.smartshed.bean;

/**
 * 阈值信息
 */
public class Threshold {
	// 传感器标识
	private int sensorID;
	// 参数名称
	private String name;
	// 最大值
	private int max;
	// 最小值
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
