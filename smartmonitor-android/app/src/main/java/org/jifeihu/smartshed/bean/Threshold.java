package org.jifeihu.smartshed.bean;

/**
 * 阈值
 */
public class Threshold {
	private Integer id;
	private Integer max;
	private Integer min;
	private Sensor sensor;

	public Threshold(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	@Override
	public String toString() {
		return "Threshold [Sensor=" + sensor + ", max="
				+ max + ", min=" + min + "]";
	}

}
