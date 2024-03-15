package com.tree.gdhealth.utils.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.tree.gdhealth.customer.chat.ChatMapper;
import com.tree.gdhealth.dto.ChatMessage;
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
	private List<HashMap<String, Object>> roomSessionsList = new ArrayList<>();

	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		boolean isRoomExists = false;
		String url = session.getUri().toString();
		int urlRoomNo =  Integer.parseInt(url.split("/chatting/")[1]);
		int roomIndex = -1;

		if (!roomSessionsList.isEmpty()) {
			for (int i = 0; i < roomSessionsList.size(); i++) {
				int sessionRoomNo = (Integer) roomSessionsList.get(i).get("roomNo");

				if (sessionRoomNo == urlRoomNo) {
					isRoomExists = true;
					roomIndex = i;
					break;
				}
			}

		}

		if (isRoomExists) {
			HashMap<String, Object> map = roomSessionsList.get(roomIndex);
			map.put(session.getId(), session);
		} else {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(session.getId(), session);
			map.put("roomNo", urlRoomNo);
			roomSessionsList.add(map);
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
		HashMap<String, Object> roomMap = new HashMap<>();

		if (!roomSessionsList.isEmpty()) {
			String messageContent = (String) jsonObject.get("msg");
			String status = (String) jsonObject.get("status");
			int dbIndex = Integer.parseInt((String) jsonObject.get("indexNo"));

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

			for (HashMap<String, Object> map : roomSessionsList) {
				int sessionListRoomNo = (Integer) map.get("roomNo");
				if (sessionListRoomNo == messageRoomNo) {
					roomMap = map;
					break;
				}
			}

			for (String k : roomMap.keySet()) {

				if ("roomNo".equals(k)) {
					continue;
				}

				WebSocketSession wss = (WebSocketSession) roomMap.get(k);

				if (wss != null) {
					try {
						wss.sendMessage(new TextMessage(jsonObject.toJSONString()));
					} catch (IOException e) {
						log.error("웹소켓 세션으로 메시지 전송 중 오류 발생 : {}", e.getMessage());
					}

				}

			}

		}

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		if (!roomSessionsList.isEmpty()) {
			for (HashMap<String, Object> roomMap : roomSessionsList) {
				roomMap.remove(session.getId());
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
