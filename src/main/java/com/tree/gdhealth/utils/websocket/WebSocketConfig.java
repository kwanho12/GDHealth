package com.tree.gdhealth.utils.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final WebSocketHandler webSocketHandler;

	/**
	 * @apiNote HttpSessionHandshakeInterceptor를 사용하여 HttpSession에 저장 된 사용자id를
	 *          WebSocketSession에서도 사용할 수 있게 하였습니다.
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "/chatting/{roomNo}")
				.addInterceptors(new HttpSessionHandshakeInterceptor())
				.setAllowedOrigins("http://localhost:80", "http://43.203.85.254", "http://52.78.98.70");
	}
}
