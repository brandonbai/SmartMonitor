package org.jifeihu.smartshed.bean;

/**
 * Created by Administrator on 2017/3/18.
 */

public class SensorDetailItem {

    private String value;
    private String info;
    private String unit;

    public SensorDetailItem() {}

    public SensorDetailItem(String value, String info, String unit) {
        this.value = value;
        this.info = info;
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
