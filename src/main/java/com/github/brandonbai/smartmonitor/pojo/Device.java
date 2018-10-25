package com.github.brandonbai.smartmonitor.pojo;

/**
 * 
 * Device 
 * 受控设备
 * @author brandonbai
 * @since 2016年10月11日
 *
 */
public class Device {

	private Integer id;
	private String name;
	private Integer nodeId;
	private Integer areaId;
	private Boolean state;

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

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	
}
