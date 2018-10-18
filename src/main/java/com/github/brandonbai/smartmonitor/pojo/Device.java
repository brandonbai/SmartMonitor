package com.github.brandonbai.smartmonitor.pojo;

/**
 * 
 * Device 
 * 受控设备
 * @author brandonbai
 * @since 2016年10月11日
 *
 */
public class Device extends BaseDevice {
	
	private Boolean state;
	
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	
}
