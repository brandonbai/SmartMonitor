package com.jifeihu.smartmonitor.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jifeihu.smartmonitor.pojo.*;
import com.jifeihu.smartmonitor.service.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jifeihu on 2017/3/30.
 */
public class MonitorWebSocketHandler implements WebSocketHandler {

	private static final Logger LOGGER = Logger.getLogger(MonitorWebSocketHandler.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private Map<Integer, Integer> realTimeValueMap = new ConcurrentHashMap<Integer, Integer>();
    private WebSocketSession webSocketSession;
    @Autowired
    private LogService logService;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private DeviceService deviceService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	// 只接收一个下位机的连接
    	if(webSocketSession != null) {
    		session.close();
    	}else {
    		webSocketSession = session;
    		showAndAddLog("与下位机建立连接", Log.INFORMATION_CONNECT);
    	}
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        LOGGER.info("接收到下位机消息："+message.getPayload().toString());
        // 获取全部传感器信息
        List<Sensor> sensorList = sensorService.findAllSensor(0,0);
        Map<Integer, Sensor> map = new HashMap<>();
        for (Sensor sensor : sensorList) {
            map.put(sensor.getId(), sensor);
        }
        Map<Integer, Integer> valueList = MAPPER.readValue(message.getPayload().toString(),
                MAPPER.getTypeFactory().constructMapType(Map.class, Integer.class, Integer.class));

        for (Integer sId : valueList.keySet()) {
            if(map.containsKey(sId)) {
            	// 更新实时监测值
                realTimeValueMap.put(sId,valueList.get(sId));
                // 入库
                sensorService.addSensorValue(sId, valueList.get(sId));
                // 获取监测值对应的传感器信息
                Sensor sensor = map.get(sId);
                // 获取阈值
                Threshold threshold = sensor.getThreshold();
                // 获取与传感器关联的设备
                Device device = deviceService.getDevice(sensor.getDeviceId());
                if(device == null) {
                	continue;
                }
                // 阈值校验，自动控制
                if(valueList.get(sId)<threshold.getMin() && !device.getState()) {
                	showAndAddLog("当前"+sensor.getName()+"值为："+valueList.get(sId)+"，低于最小值"+threshold.getMin(), Log.OUT_OF_THRESHOLD);
                    Command command = deviceService.getAutoCommand(sensor.getDeviceId(), "on");
                    if(sendMessage(command.getCommand())) {
                    	deviceService.updateDeviceState(device.getId(), true);
                    	String content = "设备状态为："+device.getState()+"，系统自动向设备"+device.getName()+"发出控制："+command.getName();
                    	showAndAddLog(content, Log.CONTROL_DEVICE);
                    }
                }else if(valueList.get(sId)>=threshold.getMin() && device.getState()) {
            		Command command = deviceService.getAutoCommand(sensor.getDeviceId(), "off");
                    if(sendMessage(command.getCommand())) {
                    	deviceService.updateDeviceState(device.getId(), true);
                    	showAndAddLog("当前"+sensor.getName()+"值为："+valueList.get(sId)+"，正常，设备状态为："+device.getState()+"，系统自动向设备"+device.getName()+"发出控制："+command.getName(), Log.CONTROL_DEVICE);
                    }
                	
                }
            }
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        showAndAddLog("下位机连接错误："+exception, Log.INFORMATION_CONNECT);
        webSocketSession = null;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        showAndAddLog("与下位机断开连接", Log.INFORMATION_CONNECT);
        webSocketSession = null;
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public boolean sendMessage(String message) {
        if(!isOpen()) {
            return false;
        }
        try {
            webSocketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }


    public boolean isOpen() {
        return webSocketSession != null && webSocketSession.isOpen();
    }
    /**
     * 获取实时监测值
     * @param key 传感器ID
     * @return
     */
    public int getRealTimeValue(int key) {
        if(realTimeValueMap.containsKey(key)) {
            return realTimeValueMap.get(key);
        }
        return 0;
    }
    
    /**
     * 控制台打印日志并入库
     * @param content 日志内容
     * @param type 日志类型
     */
    private void showAndAddLog(String content, int type) {
        Log log = new Log();
        log.setContent(content);
        log.setTime(new Date());
        log.setType(type);
        if(webSocketSession != null) {
        	User user = tokenService.getUser(webSocketSession.getHandshakeHeaders().get("token").get(0));
        	log.setUsername(user.getId()+"");
        }
        logService.addLog(log);
        LOGGER.info(log);
    }
    
}