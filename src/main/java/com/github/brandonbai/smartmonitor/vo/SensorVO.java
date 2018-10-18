package com.github.brandonbai.smartmonitor.vo;

import com.github.brandonbai.smartmonitor.pojo.Sensor;

/**
 * SensorVO
 * @author brandonbai
 * @since 2018/10/18
 */
public class SensorVO extends Sensor {

    /**
     * 实时值
     */
    private Double realValue;

    public Double getRealValue() {
        return realValue;
    }

    public void setRealValue(Double realValue) {
        this.realValue = realValue;
    }
}
