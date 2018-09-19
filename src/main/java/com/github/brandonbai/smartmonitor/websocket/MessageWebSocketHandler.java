//package com.github.brandonbai.smartmonitor.websocket;
//
//import java.text.SimpleDateFormat;
//import java.util.Map;
//import java.util.concurrent.ConcurrentSkipListMap;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.brandonbai.smartmonitor.pojo.Log;
//import com.github.brandonbai.smartmonitor.pojo.User;
//import com.github.brandonbai.smartmonitor.service.TokenService;
//
///**
// *
// * MessageWebSocketHandler
// * WebSocket消息处理类
// * @author Feihu Ji
// * @since 2017年3月30日
// *
// */
//public class MessageWebSocketHandler implements WebSocketHandler {
//
//	private static final Logger logger = Logger.getLogger(MessageWebSocketHandler.class);
//    private static final ObjectMapper MAPPER = new ObjectMapper();
//    private Map<Integer, WebSocketSession> sessionConcurrentSkipListMap = new ConcurrentSkipListMap<>();
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) {
//    	User user;
//    	try {
//    		user = tokenService.getUser(session.getHandshakeHeaders().get("token").get(0));
//		} catch (Exception e) {
//			user = (User)session.getAttributes().get("_user");
//		}
//        sessionConcurrentSkipListMap.put(user.getId(), session);
//        logger.info(user.getUsername()+"与服务器建立消息通知WebSocket");
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
//
//        logger.info("收到客户端消息"+message.getPayload().toString());
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) {
//        logger.info("发生错误"+exception.getMessage());
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
//    	User user;
//    	try {
//    		user = tokenService.getUser(session.getHandshakeHeaders().get("token").get(0));
//		} catch (Exception e) {
//			user = (User)session.getAttributes().get("_user");
//		}
//        sessionConcurrentSkipListMap.remove(user.getId());
//        logger.info(user.getUsername()+"关闭与客户端的连接");
//    }
//
//    @Override
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//
//    /**
//     * 向除了userId对应的用户之外的其他在线用户发送消息
//     * @param userId 被排除在消息发送对象之外的用户的ID
//     * @param log 日志对象
//     */
//    public void sendMessage(Integer userId, Log log) {
//        try{
//            MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//            String msg = MAPPER.writeValueAsString(log);
//            for (Map.Entry<Integer, WebSocketSession> entry : sessionConcurrentSkipListMap.entrySet()) {
//                WebSocketSession session = entry.getValue();
//                if(!entry.getKey().equals(userId) && session.isOpen()) {
//                    try {
//                        session.sendMessage(new TextMessage(msg));
//                    } catch (Exception e) {
//                        logger.error("向ID为："+entry.getKey()+"的用户发送消息时出错"+e.getMessage());
//                    }
//                }
//            }
//            logger.info("向所有用户发送消息："+msg);
//        }catch (Exception e){
//            logger.error("log对象转josn出错："+e.getMessage());
//        }
//    }
//
//}