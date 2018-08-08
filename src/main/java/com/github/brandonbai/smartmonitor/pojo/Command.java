package com.github.brandonbai.smartmonitor.pojo;

/**
 * 
 * Command 
 * 设备指令
 * @author Feihu Ji
 * @since 2017年3月25日
 *
 */
public class Command {
    private Integer deviceId;
    private String name;
    private String command;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
