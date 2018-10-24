package com.github.brandonbai.smartmonitor.service;

import com.github.brandonbai.smartmonitor.pojo.Threshold;

import java.util.Map;

/**
 * redisService
 * @author brandonbai
 * @since 2018/10/18
 */
public interface RedisService {

    /**
     * 获取实时值
     * @param sensorId
     * @return
     */
    Double getRealValue(Integer sensorId);

    /**
     * 存储实时值
     * @param sensorId
     * @param value
     */
    void setRealValue(Integer sensorId, Double value);

    /**
     * 设置阈值
     * @param threshold
     */
    void setThreshold(Threshold threshold);

    /**
     * 获取阈值
     * @param thresholdId
     * @return
     */
    Threshold getThreshold(Integer thresholdId);

    /**
     * 获取首页统计信息
     * @return
     */
    Map<String,String> getDashboardStatisticInfo();
}
