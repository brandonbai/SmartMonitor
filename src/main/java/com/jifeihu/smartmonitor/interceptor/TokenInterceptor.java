package com.jifeihu.smartmonitor.interceptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jifeihu.smartmonitor.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 * 
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(TokenInterceptor.class);

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("token拦截器拦截到请求"+request.getRequestURI());
        // 检查 token 有效性
        return tokenService.checkToken();
    }


}
