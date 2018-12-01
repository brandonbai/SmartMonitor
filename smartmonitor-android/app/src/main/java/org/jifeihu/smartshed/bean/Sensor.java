package org.jifeihu.smartshed.bean;

/**
 * 传感器
 */
public class Sensor extends BaseDevice {
	
	private String unit;
	private Threshold threshold;
	
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

}
