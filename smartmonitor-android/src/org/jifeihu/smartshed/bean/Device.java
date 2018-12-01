package org.jifeihu.smartshed.bean;

/**
 * �豸��Ϣ
 * 
 */
public class Device {
	// ״̬
	private boolean isOpen;
	// ����
	private String name;
	// ��ָ��
	private String commandOn;
	// ��ָ��
	private String commandOff;
	
	public Device(boolean isOpen, String name, String commandOn,
			String commandOff) {
		super();
		this.isOpen = isOpen;
		this.name = name;
		this.commandOn = commandOn;
		this.commandOff = commandOff;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommandOn() {
		return commandOn;
	}

	public void setCommandOn(String commandOn) {
		this.commandOn = commandOn;
	}

	public String getCommandOff() {
		return commandOff;
	}

	public void setCommandOff(String commandOff) {
		this.commandOff = commandOff;
	}

	@Override
	public String toString() {
		return "Device [isOpen=" + isOpen + ", name=" + name + ", commandOn="
				+ commandOn + ", commandOff=" + commandOff + "]";
	}
	
	
}
