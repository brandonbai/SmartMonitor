package com.github.brandonbai.smartmonitor.vo;

import com.github.brandonbai.smartmonitor.pojo.Device;

/**
 * 设备vo
 */
public class DeviceVO extends Device {

    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
