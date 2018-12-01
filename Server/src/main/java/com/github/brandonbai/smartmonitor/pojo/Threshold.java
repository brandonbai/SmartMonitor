package com.github.brandonbai.smartmonitor.pojo;

import io.swagger.models.auth.In;

/**
 * 
 * Threshold 
 * 阈值
 * @author brandonbai
 * @since 2016年10月11日
 *
 */
public class Threshold {

	private Integer id;
	private Integer max;
	private Integer min;
	private Integer sensorId;

	public Threshold(){}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
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
