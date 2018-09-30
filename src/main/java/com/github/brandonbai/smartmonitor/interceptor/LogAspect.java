package com.github.brandonbai.smartmonitor.interceptor;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.brandonbai.smartmonitor.pojo.Log;
import com.github.brandonbai.smartmonitor.pojo.Threshold;
import com.github.brandonbai.smartmonitor.service.LogService;
import com.github.brandonbai.smartmonitor.service.TokenService;

/**
 * 
 * LogAspect 
 * 日志存储切面
 * @author Feihu Ji
 * @since 2016年10月15日
 *
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private LogService logService;


    @AfterReturning("execution(* com.github.brandonbai.smartmonitor.service..*.updateThresholds(..))")
    public void saveLogUpdateThreshold(JoinPoint pjp) {
        Threshold threshold = (Threshold) pjp.getArgs()[0];
        Log log = new Log();
        log.setContent("修改id为"+threshold.getId()+"的阈值为："+threshold.getMin()+"~"+threshold.getMax());
        log.setTime(new Date());
        log.setType(Log.CHANGE_THRESHOLD);
        log.setUsername(tokenService.getUser().getId()+"");
        logService.addLog(log);
    }
    @AfterReturning("execution(* com.github.brandonbai.smartmonitor.service..*.controlDevice(..))")
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
