package com.tree.gdhealth.utils.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.tree.gdhealth.customer.chat.ChatService;
import com.tree.gdhealth.dto.ChatMessageDto;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.headoffice.chat.AdministratorChatService;

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

	private final AdministratorChatService adminChatService;
	private final ChatService customerChatService;

	private Map<Integer, ConcurrentHashMap<String, WebSocketSession>> roomSessionsMap = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws IOException {

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

		String employeeId = (loginEmployee != null) ? loginEmployee.getEmployeeId() : null;
		String customerId = (String) session.getAttributes().get("customerId");

		JSONObject obj = new JSONObject();
		obj.put("type", "getId");
		obj.put("id", (customerId != null) ? customerId : employeeId);

		session.sendMessage(new TextMessage(obj.toJSONString()));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

		JSONObject jsonObject = jsonToObjectParser(message.getPayload());

		int messageRoomNo = Integer.parseInt((String) jsonObject.get("roomNo"));
		String messageContent = (String) jsonObject.get("msg");
		String status = (String) jsonObject.get("status");
		int dbIndex = Integer.parseInt((String) jsonObject.get("indexNo"));

		if (!roomSessionsMap.isEmpty()) {
			ChatMessageDto chatMessageDto = new ChatMessageDto();
			chatMessageDto.setChatRoomNo(messageRoomNo);
			chatMessageDto.setMessageContent(messageContent);

			saveChatMessage(status, dbIndex, chatMessageDto);
			sendChatMessages(jsonObject, roomSessionsMap.get(messageRoomNo));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		roomSessionsMap.forEach((key, sessionMap) -> {
			sessionMap.remove(session.getId());
			if (sessionMap.isEmpty()) {
				roomSessionsMap.remove(key);
			}
		});
	}

	/**
	 * 상태에 따라 채팅 메시지를 적절한 서비스에 저장합니다.
	 *
	 * @param status      메시지가 고객으로부터 온 것인지 직원으로부터 온 것인지를 나타내는 상태
	 * @param dbIndex     고객 또는 직원의 데이터베이스 인덱스
	 * @param chatMessage 저장할 {@code ChatMessage} 객체
	 */
	private void saveChatMessage(String status, int dbIndex, ChatMessageDto chatMessageDto) {
		boolean isCustomer = status.equals("customer");
		chatMessageDto.setCustomerNo(isCustomer ? dbIndex : null);
		chatMessageDto.setEmployeeNo(isCustomer ? null : dbIndex);

		if (chatMessageDto.getCustomerNo() != null) {
			customerChatService.saveMessage(chatMessageDto);
		} else {
			adminChatService.saveMessage(chatMessageDto);
		}
	}

	/**
	 * 채팅 메시지를 세션에 전송합니다.
	 *
	 * @param jsonObject 전송할 메시지의 JSON 객체
	 * @param sessionMap 메시지를 보낼 세션 맵
	 * @throws IOException 웹소켓 세션으로 메시지 전송 중 발생한 예외
	 */
	private void sendChatMessages(JSONObject jsonObject, ConcurrentHashMap<String, WebSocketSession> sessionMap)
			throws IOException {
		sessionMap.values().forEach(session -> {
			try {
				session.sendMessage(new TextMessage(jsonObject.toJSONString()));
			} catch (IOException e) {
				log.error("웹소켓 세션으로 메시지 전송 중 오류 발생", e);
			}
		});
	}

	/**
	 * JSON 문자열을 JSONObject로 파싱하여 리턴합니다.
	 * 
	 * @param jsonStr JSON 형태의 문자열
	 * @return JSON 문자열을 JSONObject로 파싱한 객체
	 */
	private JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			log.error("JSON parsing 중 오류 발생", e);
		}
		return obj;
	}
}
