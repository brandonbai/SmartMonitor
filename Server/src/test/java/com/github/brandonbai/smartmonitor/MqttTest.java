package com.github.brandonbai.smartmonitor;

import com.github.brandonbai.smartmonitor.config.MqttConfig;
import com.github.brandonbai.smartmonitor.mqtt.MqttMessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqttTest {

    @Autowired
    private MqttConfig.DefaultMqttMessageSender defaultMqttMessageSender;

    @Autowired
    private MqttMessageSender mqttMessageSender;

    @Test
    public void sendTest() {
        // 默认主题
        defaultMqttMessageSender.sendToMqtt("123");

        // 自定义主题
        mqttMessageSender.sendMessage("sm/123", "haha");
    }
}
