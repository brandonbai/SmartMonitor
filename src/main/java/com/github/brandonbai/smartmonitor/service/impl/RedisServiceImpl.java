package com.github.brandonbai.smartmonitor.service.impl;

import static com.github.brandonbai.smartmonitor.utils.RedisKeyConstant.*;

import com.alibaba.fastjson.JSON;
import com.github.brandonbai.smartmonitor.mapper.DeviceMapper;
import com.github.brandonbai.smartmonitor.mapper.LogMapper;
import com.github.brandonbai.smartmonitor.mapper.SensorMapper;
import com.github.brandonbai.smartmonitor.pojo.Log;
import com.github.brandonbai.smartmonitor.pojo.Threshold;
import com.github.brandonbai.smartmonitor.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * redisService
 * @author brandonbai
 * @since 2018/10/18
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private SensorMapper sensorMapper;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private LogMapper logMapper;

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

    @Override
    public Map<String, String> getDashboardStatisticInfo() {
        String sensorNum = redisTemplate.opsForValue().get(DASHBOARD_SENSOR_NUMBER);
        String messageNum = redisTemplate.opsForValue().get(DASHBOARD_MESSAGE_NUMBER);
        String deviceNum = redisTemplate.opsForValue().get(DASHBOARD_DEVICE_NUMBER);
        String warnNum = redisTemplate.opsForValue().get(DASHBOARD_WARN_NUMBER);
        if(sensorNum == null) {
            sensorNum = String.valueOf(sensorMapper.selectCount(null));
            redisTemplate.opsForValue().set(DASHBOARD_SENSOR_NUMBER, sensorNum);
        }
        if(messageNum == null) {
            messageNum = String.valueOf(sensorMapper.selectCount(null));
            redisTemplate.opsForValue().set(DASHBOARD_MESSAGE_NUMBER, messageNum);
        }
        if(deviceNum == null) {
            deviceNum = String.valueOf(deviceMapper.selectCount(null));
            redisTemplate.opsForValue().set(DASHBOARD_DEVICE_NUMBER, deviceNum);
        }
        if(warnNum == null) {
            Log log = new Log();
            log.setType(Log.OUT_OF_THRESHOLD);
            warnNum = String.valueOf(logMapper.selectCount(log));
            redisTemplate.opsForValue().set(DASHBOARD_WARN_NUMBER, warnNum);
        }

        Map<String, String> map = new HashMap<>();
        map.put(DASHBOARD_SENSOR_NUMBER, sensorNum);
        map.put(DASHBOARD_MESSAGE_NUMBER, messageNum);
        map.put(DASHBOARD_DEVICE_NUMBER, deviceNum);
        map.put(DASHBOARD_WARN_NUMBER, warnNum);

        return map;
    }

    private String formatSensorId(Integer key) {
        return String.format("sensor_%d", key);
    }

    private String formatSensorThresholdId(Integer key) {
        return String.format("thr_%d", key);
    }
}
