package com.github.brandonbai.smartmonitor.interception;

import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.brandonbai.smartmonitor.annotation.RolePermission;
import com.github.brandonbai.smartmonitor.annotation.RoleType;
import com.github.brandonbai.smartmonitor.pojo.Log;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.brandonbai.smartmonitor.service.LogService;
import com.github.brandonbai.smartmonitor.service.TokenService;

/**
 * 权限控制切面
 *
 */
@Aspect
@Component
public class PermissionAspect {

    private static final Logger LOGGER = Logger.getLogger(PermissionAspect.class);

    @Autowired
    private TokenService tokenService;
    @Autowired
    private LogService logService;
    
    @Around("execution(* org.jifeihu.smartshed.controller..*(..)) && @annotation(rolePermission)")
    public Object execute(ProceedingJoinPoint pjp, RolePermission rolePermission) throws Throwable {
        LOGGER.info("权限控制拦截器拦截请求");
        // 权限验证
        if(rolePermission.value().equals(RoleType.ROLE_ADMIN)) {
            User user = tokenService.getUser();
            if(user.getPower()<100) {
                Log log = new Log();
                log.setType(Log.CONTROL_DEVICE);
                log.setTime(new Date());
                log.setContent("无权限用户尝试无权操作-"+pjp.getSignature().getName());
                log.setUsername(user.getId()+"");
                logService.addLog(log);
                return new Response().failure("无权限");
            }
        }
        // 调用目标方法
        return pjp.proceed();
    }
}