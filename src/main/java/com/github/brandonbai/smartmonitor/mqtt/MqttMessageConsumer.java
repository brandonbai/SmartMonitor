package com.github.brandonbai.smartmonitor.mqtt;

import com.alibaba.fastjson.JSON;
import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.brandonbai.smartmonitor.service.RedisService;
import com.github.brandonbai.smartmonitor.service.SensorService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * mqtt消费者
 * @author brandonbai
 * @since 2018/10/06
 */
public class MqttMessageConsumer implements MessageHandler {

    private RedisService redisService;

    private SensorService sensorService;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String payload = (String)message.getPayload();
        SensorValue sv = JSON.parseObject(payload, SensorValue.class);
        redisService.setRealValue(sv.getSensorId(), sv.getValue());
        sensorService.addSensorValue(sv.getSensorId(), sv.getValue());
    }

    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
