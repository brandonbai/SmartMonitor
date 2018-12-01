package com.github.brandonbai.smartmonitor.dto;

import com.github.brandonbai.smartmonitor.pojo.Sensor;
import com.github.brandonbai.smartmonitor.pojo.Threshold;

/**
 * SensorDTO
 * @author brandonbai
 * @since 2018/10/18
 */
public class SensorDTO extends Sensor {

    private Threshold threshold;

    public Threshold getThreshold() {
        return threshold;
    }

    public void setThreshold(Threshold threshold) {
        this.threshold = threshold;
    }
}
