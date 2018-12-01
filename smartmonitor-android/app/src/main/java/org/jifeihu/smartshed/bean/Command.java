package org.jifeihu.smartshed.bean;

/**
 * Created by Administrator on 2017/3/25.
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
