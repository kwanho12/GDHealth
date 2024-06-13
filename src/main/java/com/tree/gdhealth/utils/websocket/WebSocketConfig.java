package com.tree.gdhealth.utils.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import lombok.RequiredArgsConstructor;

/**
 * 웹소켓 설정 클래스
 * 
 * @author 진관호
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final WebSocketHandler webSocketHandler;

	/**
	 * 웹소켓 핸들러를 등록합니다.
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "/chatting/{roomNo}")
				.addInterceptors(new HttpSessionHandshakeInterceptor())
				.setAllowedOrigins("http://localhost:80");
	}
}
