package com.github.brandonbai.smartmonitor.interceptor;

import java.util.Date;

import com.github.brandonbai.smartmonitor.utils.TokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.brandonbai.smartmonitor.annotation.RolePermission;
import com.github.brandonbai.smartmonitor.annotation.RoleType;
import com.github.brandonbai.smartmonitor.pojo.Log;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.brandonbai.smartmonitor.service.LogService;

/**
 * 
 * PermissionAspect 
 * 权限控制切面
 * @author Feihu Ji
 * @since 2016年10月15日
 *
 */
@Aspect
@Component
public class PermissionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionAspect.class);

    @Autowired
    private LogService logService;
    
    @Around("execution(* com.github.brandonbai.smartmonitor.controller..*(..)) && @annotation(rolePermission)")
    public Object execute(ProceedingJoinPoint pjp, RolePermission rolePermission) throws Throwable {
        LOGGER.info("权限控制拦截器拦截请求");
        // 权限验证
        if(rolePermission.value().equals(RoleType.ROLE_ADMIN)) {
            User user = TokenUtil.getUser();
            if(user.getRoleId() != RoleType.ROLE_ADMIN.getId()) {
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
