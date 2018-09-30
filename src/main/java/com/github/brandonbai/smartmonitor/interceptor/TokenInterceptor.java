package com.github.brandonbai.smartmonitor.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.brandonbai.smartmonitor.utils.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * TokenInterceptor 
 * token拦截器
 * @author Feihu Ji
 * @since 2016年11月11日
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TokenUtil.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
