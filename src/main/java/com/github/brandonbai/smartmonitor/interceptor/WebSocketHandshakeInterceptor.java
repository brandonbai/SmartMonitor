//package com.github.brandonbai.smartmonitor.interceptor;
//
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import com.github.brandonbai.smartmonitor.service.TokenService;
//
///**
// *
// * WebSocketHandshakeInterceptor
// * WebSocket握手拦截器
// * @author Feihu Ji
// * @since 2016年11月11日
// *
// */
//public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
//
//    private static final Logger logger = Logger.getLogger(WebSocketHandshakeInterceptor.class);
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                               WebSocketHandler webSocketHandler, Exception e) {
//        logger.info("afterHandshake");
//
//    }
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
//                                   Map<String, Object> attribute) {
//        logger.info("beforeHandshake "+request.getURI());
//        if(request instanceof ServletServerHttpRequest) {
//            return tokenService.checkToken();
//        }
//        return false;
//    }
//}
//
