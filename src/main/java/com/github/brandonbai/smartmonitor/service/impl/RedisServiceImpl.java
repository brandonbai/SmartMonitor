package com.github.brandonbai.smartmonitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.brandonbai.smartmonitor.mqtt.MqttMessageConsumer;
import com.github.brandonbai.smartmonitor.pojo.Threshold;
import com.github.brandonbai.smartmonitor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * redisService
 * @author brandonbai
 * @since 2018/10/18
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private MqttMessageConsumer mqttMessageConsumer;

    @Autowired
    public void setMqttMessageConsumer(MqttMessageConsumer mqttMessageConsumer) {
        this.mqttMessageConsumer = mqttMessageConsumer;
        this.mqttMessageConsumer.setRedisService(this);
    }

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
    public Threshold getThreshold(Integer thresholdId) {
        String value = redisTemplate.opsForValue().get(thresholdId);
        return JSON.parseObject(value, Threshold.class);
    }

    private String formatSensorId(Integer key) {
        return String.format("sensor_%d", key);
    }

    private String formatSensorThresholdId(Integer key) {
        return String.format("thr_%d", key);
    }
}
