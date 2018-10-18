package com.github.brandonbai.smartmonitor.mqtt;

import com.alibaba.fastjson.JSON;
import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.brandonbai.smartmonitor.service.SensorService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * mqtt消费者
 * @author brandonbai
 * @since 2018/10/06
 */
public class MqttMessageConsumer implements MessageHandler {

    private RedisTemplate<String, String> redisTemplate;

    private SensorService sensorService;

    public MqttMessageConsumer(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String payload = (String)message.getPayload();
        SensorValue sv = JSON.parseObject(payload, SensorValue.class);
        redisTemplate.opsForValue().set(String.format("sensor_%d", sv.getSensorId()), String.valueOf(sv.getValue()));
        sensorService.addSensorValue(sv.getSensorId(), sv.getValue());
    }

    public Integer getValue(Integer key) {
        String result = redisTemplate.opsForValue().get(String.format("sensor_%d", key));
        return result == null ? null : Integer.parseInt(result);
    }

    public SensorService getSensorService() {
        return sensorService;
    }

    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }
}
