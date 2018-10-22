package com.github.brandonbai.smartmonitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.brandonbai.smartmonitor.pojo.Threshold;
import com.github.brandonbai.smartmonitor.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * redisService
 * @author brandonbai
 * @since 2018/10/18
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Double getRealValue(Integer sensorId) {
        String value = redisTemplate.opsForValue().get(formatSensorId(sensorId));
        return Double.parseDouble(value);
    }

    @Override
    public void setRealValue(Integer sensorId, Double value) {
        redisTemplate.opsForValue().set(formatSensorId(sensorId), String.valueOf(value));
    }

    @Override
    public void setThreshold(Threshold threshold) {
        Integer sensorId = threshold.getSensorId();
        redisTemplate.opsForValue().set(formatSensorThresholdId(sensorId), JSON.toJSONString(threshold));
    }

    @Override
    public Threshold getThreshold(Integer sensorId) {
        String value = redisTemplate.opsForValue().get(formatSensorThresholdId(sensorId));
        return JSON.parseObject(value, Threshold.class);
    }

    private String formatSensorId(Integer key) {
        return String.format("sensor_%d", key);
    }

    private String formatSensorThresholdId(Integer key) {
        return String.format("thr_%d", key);
    }
}
