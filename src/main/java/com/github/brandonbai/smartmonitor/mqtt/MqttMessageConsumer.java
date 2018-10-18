package com.github.brandonbai.smartmonitor.mqtt;

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
@Component
public class MqttMessageConsumer implements MessageHandler {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private SensorService sensorService;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();
        SensorValue sv = (SensorValue) payload;
        redisTemplate.opsForValue().set(String.valueOf(sv.getSensorId()), String.valueOf(sv.getValue()));
        sensorService.addSensorValue(sv.getSensorId(), sv.getValue());
    }

    public Integer getValue(Integer key) {
        String result = redisTemplate.opsForValue().get(String.valueOf(key));
        return result == null ? null : Integer.parseInt(result);
    }
}
