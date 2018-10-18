package com.github.brandonbai.smartmonitor.pojo;

/**
 * 
 * BaseDevice 
 * 设备基类
 * @author brandonbai
 * @since 2016年10月11日
 *
 */
public class BaseDevice {
	private Integer id;
	private String name;
	private String nodeName;
	private String areaName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
}
