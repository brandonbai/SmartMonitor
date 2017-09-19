package com.jifeihu.smartmonitor.interceptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.jifeihu.smartmonitor.service.TokenService;

import java.util.Map;

/**
 * WebSocket握手拦截器
 * 
 */

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = Logger.getLogger(WebSocketHandshakeInterceptor.class);

    @Autowired
    private TokenService tokenService;

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler webSocketHandler, Exception e) {
        logger.info("afterHandshake");

    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
                                   Map<String, Object> attribute) throws Exception {
        logger.info("beforeHandshake "+request.getURI());
        if(request instanceof ServletServerHttpRequest) {
            return tokenService.checkToken();
        }
        return false;
    }
}

