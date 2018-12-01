package org.jifeihu.smartshed.bean;

import java.io.Serializable;

public class SensorValue implements Serializable {
	
	private String time;
	private int value;

	public SensorValue() {
	}

	public SensorValue(String time, int value) {
		this.time = time;
		this.value = value;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SensorValue{" +
				"time=" + time +
				", value=" + value +
				'}';
	}
}
