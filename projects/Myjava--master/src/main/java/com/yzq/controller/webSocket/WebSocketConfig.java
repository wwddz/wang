package com.yzq.controller.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer, WebMvcConfigurer {
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    //WebIM WebSocket通道
    registry.addHandler(chatWebSocketHandler(), "/webSocketIMServer");
    registry.addHandler(chatWebSocketHandler(), "/sockjs/webSocketIMServer");
    registry.addHandler(chatWebSocketHandler(), "/sockjs/webSocketIMServer").withSockJS();
  }

  @Bean
  public ChatWebSocketHandler chatWebSocketHandler() {
    return new ChatWebSocketHandler();
  }
}
