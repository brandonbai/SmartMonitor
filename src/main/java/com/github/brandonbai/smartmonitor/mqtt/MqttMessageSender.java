package com.github.brandonbai.smartmonitor.mqtt;

import com.alibaba.fastjson.JSON;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author brandonbai
 * @since 2018/10/06
 */
@Component
public class MqttMessageSender {

    @Resource
    private MqttPahoMessageHandler mqttPahoMessageHandler;

    public void sendMessage(String topic, Object content) {
        String message;
        if(content instanceof String) {
            message = (String) content;
        }else {
            message = JSON.toJSONString(content);
        }
        // 构建消息
        Message<String> messages = MessageBuilder.withPayload(message).setHeader(MqttHeaders.TOPIC, topic).build();
        // 发送消息
        mqttPahoMessageHandler.handleMessage(messages);
    }
}
