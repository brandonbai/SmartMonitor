package com.github.brandonbai.smartmonitor.mqtt;

import com.github.brandonbai.smartmonitor.pojo.SensorValue;
import com.github.brandonbai.smartmonitor.service.SensorService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.HashMap;
import java.util.Map;

/**
 * mqtt消费者
 */
public class MqttMessageConsumer implements MessageHandler {

    private Map<Integer, Integer> map = new HashMap<>();

    private SensorService sensorService;

    public MqttMessageConsumer(SensorService sensorService){
        this.sensorService = sensorService;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();
        SensorValue sv = (SensorValue) payload;
        map.put(sv.getSensorId(), sv.getValue());
        sensorService.addSensorValue(sv.getSensorId(), sv.getValue());
    }

    public Integer getValue(Integer key) {
        return map.get(key);
    }
}
