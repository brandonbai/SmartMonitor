package com.github.brandonbai.smartmonitor.mqtt;

import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.brandonbai.smartmonitor.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * mqtt消费者
 */
@Component
public class MqttMessageConsumer implements MessageHandler {

    @Resource
    private RedisTemplate<Integer, Integer> redisTemplate;

    @Resource
    private SensorService sensorService;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();
        SensorValue sv = (SensorValue) payload;
        redisTemplate.opsForValue().set(sv.getSensorId(), sv.getValue());
        sensorService.addSensorValue(sv.getSensorId(), sv.getValue());
    }

    public Integer getValue(Integer key) {
        return redisTemplate.opsForValue().get(key);
    }
}
