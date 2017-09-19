package com.jifeihu.smartmonitor.interceptor;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jifeihu.smartmonitor.pojo.Log;
import com.jifeihu.smartmonitor.pojo.Threshold;
import com.jifeihu.smartmonitor.service.LogService;
import com.jifeihu.smartmonitor.service.TokenService;

/**
 * 日志存储切面
 *
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private LogService logService;


    @AfterReturning("execution(* org.jifeihu.smartshed.service..*.updateThresholds(..))")
    public void saveLogUpdateThreshold(JoinPoint pjp) {
        Threshold threshold = (Threshold) pjp.getArgs()[0];
        Log log = new Log();
        log.setContent("修改id为"+threshold.getId()+"的阈值为："+threshold.getMin()+"~"+threshold.getMax());
        log.setTime(new Date());
        log.setType(Log.CHANGE_THRESHOLD);
        log.setUsername(tokenService.getUser().getId()+"");
        logService.addLog(log);
    }
    @AfterReturning("execution(* org.jifeihu.smartshed.service..*.controlDevice(..))")
    public void saveLogControl(JoinPoint pjp) {
        String command = (String) pjp.getArgs()[0];
        Log log = new Log();
        log.setContent("发送控制设备指令："+command);
        log.setTime(new Date());
        log.setType(Log.CONTROL_DEVICE);
        log.setUsername(tokenService.getUser().getId()+"");
        logService.addLog(log);
    }

}