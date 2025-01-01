package com.springboot.healthypath.handler;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.healthypath.model.SocketMessageVO;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

  private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception { 
    String sessionIdStrings = session.getId().split("-")[0];
    
    SocketMessageVO message = new SocketMessageVO();
    message.setType("info");
    message.setSender(sessionIdStrings);
    
    String jsonMessage = objectMapper.writeValueAsString(message);
    TextMessage textMessage = new TextMessage(jsonMessage);
    session.sendMessage(textMessage); // 연결된 session에만 보냄
    
    broadcastSystemMessage(sessionIdStrings + " 님이 대화방에 입장했습니다."); // 전체 session에 보냄
    
    sessions.add(session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    SocketMessageVO receivedMessage = objectMapper.readValue(payload, SocketMessageVO.class);

    String sessionIdStrings = session.getId().split("-")[0];
    receivedMessage.setSender(sessionIdStrings); 
    receivedMessage.setType("chat");
    receivedMessage.setTimeSent(LocalDateTime.now());

    String jsonMessage = objectMapper.writeValueAsString(receivedMessage);
    broadcastToAllSessions(jsonMessage);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    sessions.remove(session);
    String sessionIdStrings = session.getId().split("-")[0];
    broadcastSystemMessage(sessionIdStrings + " 님이 대화방에서 나가셨습니다.");
  }

  private void broadcastMessage(String sender, String content) throws Exception {
    SocketMessageVO chatMessage = new SocketMessageVO();
    chatMessage.setType("chat");
    chatMessage.setSender(sender);
    chatMessage.setContent(content);
    chatMessage.setTimeSent(LocalDateTime.now());

    String jsonMessage = objectMapper.writeValueAsString(chatMessage);
    broadcastToAllSessions(jsonMessage);
  }

  private void broadcastSystemMessage(String content) throws Exception {
    broadcastMessage("System", content);
  }

  private void broadcastToAllSessions(String message) throws Exception {
    TextMessage textMessage = new TextMessage(message);
    for (WebSocketSession session : sessions) {
      if (session.isOpen()) {
        session.sendMessage(textMessage);
      }
    }
  }
}