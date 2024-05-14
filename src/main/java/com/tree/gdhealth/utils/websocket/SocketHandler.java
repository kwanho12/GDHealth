package com.tree.gdhealth.utils.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.tree.gdhealth.customer.chat.ChatMapper;
import com.tree.gdhealth.domain.ChatMessage;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.headoffice.chat.AdministratorChatMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 웹소켓 핸들러 클래스
 * 
 * @author 진관호
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {

	private final ChatMapper customerChatMapper;
	private final AdministratorChatMapper administratorChatMapper;
	private ConcurrentHashMap<Integer, ConcurrentHashMap<String, WebSocketSession>> roomSessionsMap = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		String url = session.getUri().toString();
		int urlRoomNo = Integer.parseInt(url.split("/chatting/")[1]);
		boolean isRoomExists = roomSessionsMap.get(urlRoomNo) != null ? true : false;

		if (isRoomExists) {
			roomSessionsMap.get(urlRoomNo).put(session.getId(), session);
		} else {
			ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
			sessionMap.put(session.getId(), session);
			roomSessionsMap.put(urlRoomNo, sessionMap);
		}

		LoginEmployee loginEmployee = (LoginEmployee) session.getAttributes().get("loginEmployee");
		JSONObject obj = new JSONObject();

		String employeeId = (loginEmployee != null) ? loginEmployee.getEmployeeId() : null;
		String customerId = (String) session.getAttributes().get("customerId");
		obj.put("type", "getId");
		obj.put("id", (customerId != null) ? customerId : employeeId);

		session.sendMessage(new TextMessage(obj.toJSONString()));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		JSONObject jsonObject = jsonToObjectParser(message.getPayload());

		int messageRoomNo = Integer.parseInt((String) jsonObject.get("roomNo"));
		String messageContent = (String) jsonObject.get("msg");
		String status = (String) jsonObject.get("status");
		int dbIndex = Integer.parseInt((String) jsonObject.get("indexNo"));

		if (!roomSessionsMap.isEmpty()) {
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setChatRoomNo(messageRoomNo);
			chatMessage.setMessageContent(messageContent);

			if (status.equals("customer")) {
				chatMessage.setCustomerNo(dbIndex);
				customerChatMapper.insertMessage(chatMessage);
			} else {
				chatMessage.setEmployeeNo(dbIndex);
				administratorChatMapper.insertMessage(chatMessage);
			}

			ConcurrentHashMap<String, WebSocketSession> sessionMap = roomSessionsMap.get(messageRoomNo);

			for (String k : sessionMap.keySet()) {

				WebSocketSession webSocketSession = sessionMap.get(k);

				if (webSocketSession != null) {
					try {
						webSocketSession.sendMessage(new TextMessage(jsonObject.toJSONString()));
					} catch (IOException e) {
						log.error("웹소켓 세션으로 메시지 전송 중 오류 발생 : {}", e.getMessage());
					}
				}
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		if (!roomSessionsMap.isEmpty()) {
			for (Integer key : roomSessionsMap.keySet()) {
				roomSessionsMap.get(key).remove(session.getId());
				if (roomSessionsMap.get(key).isEmpty()) {
					roomSessionsMap.remove(key);
				}
			}
		}
	}

	/**
	 * JSON 문자열을 JSONObject로 파싱하여 리턴합니다.
	 * 
	 * @param jsonStr JSON 형태의 문자열
	 * @return JSON 문자열을 JSONObject로 파싱한 객체
	 */
	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			log.error("JSON parsing 중 오류 발생 : {}", e.getMessage());
		}
		return obj;
	}
}
