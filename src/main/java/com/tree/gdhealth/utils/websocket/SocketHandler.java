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

@Slf4j
@Component
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {

	private final ChatMapper customerChatMapper;
	private final AdministratorChatMapper headofficeChatMapper;

	List<HashMap<String, Object>> roomListSessions = new ArrayList<>(); // 웹소켓 세션을 담아 둘 리스트

	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		log.debug("세션 정보 : " + session); // 세션 아이디는 랜덤 생성

		// 소켓 연결
		boolean flag = false;
		String url = session.getUri().toString();
		String roomNo = url.split("/chatting/")[1];

		int index = roomListSessions.size(); // 방의 사이즈를 조사한다.
		log.debug("방의 개수 : " + index);
		if (roomListSessions.size() > 0) {

			for (int i = 0; i < roomListSessions.size(); i++) {
				String roomNoCheck = (String) roomListSessions.get(i).get("roomNo");

				if (roomNoCheck.equals(roomNo)) {
					flag = true;
					index = i;
					break;
				}
			}

		}

		if (flag) { // roomListSession에 이미 추가된 방이라면 세션(채팅방에 접속한 사람에 대한 정보)만 추가한다.

			// 해당 방에 대한 정보를 나타내는 map을 가져 온다.
			HashMap<String, Object> map = roomListSessions.get(index);
			// 해당 방에 현재 연결된 session을 map에 추가한다.
			map.put(session.getId(), session);
			log.debug("roomListSessions(이미 존재) : " + roomListSessions);

		} else { // 아직 roomListSession에 추가되지 않은 방이라면 방 번호와 세션(처음으로 채팅방에 접속한 사람에 대한 정보)을 추가한다.

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("roomNo", roomNo);
			map.put(session.getId(), session);
			roomListSessions.add(map);
			log.debug("roomListSessions(최초 생성) : " + roomListSessions);

		}

		// 세션 등록이 끝나면 발급받은 세션ID값의 메시지를 발송한다.
		JSONObject obj = new JSONObject();
		obj.put("type", "getId");

		Object customerId = session.getAttributes().get("customerId");

		LoginEmployee loginEmployee = (LoginEmployee) session.getAttributes().get("loginEmployee");
		String employeeId = "";
		if (loginEmployee != null) {
			employeeId = loginEmployee.getEmployeeId();
		}

		if (customerId != null) {
			obj.put("id", customerId);
		} else {
			obj.put("id", employeeId);
		}

		// 디버깅
		log.debug("웹 소켓 결과 : " + obj);
		session.sendMessage(new TextMessage(obj.toJSONString()));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		// 메시지 발송
		String msg = message.getPayload();
		JSONObject obj = jsonToObjectParser(msg);

		String roomNo = (String) obj.get("roomNo");
		HashMap<String, Object> temp = new HashMap<String, Object>();

		if (roomListSessions.size() > 0) {

			// 채팅 저장
			String message2 = (String) obj.get("msg");
			String status = (String) obj.get("status");
			String indexNo = (String) obj.get("indexNo");

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setChatRoomNo(Integer.parseInt(roomNo));
			chatMessage.setMessageContent(message2);

			int insertMessage;
			if (status.equals("customer")) {
				chatMessage.setCustomerNo(Integer.parseInt(indexNo));
				insertMessage = customerChatMapper.insertMessage(chatMessage);
			} else {
				chatMessage.setEmployeeNo(Integer.parseInt(indexNo));
				insertMessage = headofficeChatMapper.insertMessage(chatMessage);
			}

			log.debug("메시지 추가(성공:1) : " + insertMessage);

			for (int i = 0; i < roomListSessions.size(); i++) {
				String roomNoCheck = (String) roomListSessions.get(i).get("roomNo"); // 세션리스트의 저장된 방 번호를 가져와서
				if (roomNoCheck.equals(roomNo)) { // 같은 값의 방이 존재한다면
					// 해당 방 번호의 세션리스트에 존재하는 모든 object값(roomNo, sessionId)을 가져온다.
					temp = roomListSessions.get(i);
					break;
				}
			}

			// 해당 방의 세션들만 찾아서 메시지를 발송해준다.
			for (String k : temp.keySet()) { // keySet : roomNo, id1, id2, ....

				if (k.equals("roomNo")) { // 다만 방 번호일 경우에는 건너 뛴다.(sessionId일 경우에만 적용)
					continue;
				}

				// 웹소켓 세션 객체에는 url이 저장되어 있어 해당되는 url로 메시지를 보내 웹소켓 통신을 가능하게 한다.
				WebSocketSession wss = (WebSocketSession) temp.get(k);
				log.debug("webSocketSessions : " + wss);

				if (wss != null) {
					try {
						wss.sendMessage(new TextMessage(obj.toJSONString()));
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			}

		}

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 소켓 종료
		if (roomListSessions.size() > 0) { // 소켓이 종료되면 해당 세션 값들을 찾아서 지운다.
			for (int i = 0; i < roomListSessions.size(); i++) {
				roomListSessions.get(i).remove(session.getId());
			}
		}

	}

	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
