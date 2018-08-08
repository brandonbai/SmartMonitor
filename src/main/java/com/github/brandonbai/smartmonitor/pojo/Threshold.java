package com.github.brandonbai.smartmonitor.pojo;

/**
 * 
 * Threshold 
 * 阈值
 * @author Feihu Ji
 * @since 2016年10月11日
 *
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

}
