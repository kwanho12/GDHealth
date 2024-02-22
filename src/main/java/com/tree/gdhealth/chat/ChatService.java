package com.tree.gdhealth.chat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.vo.ChatMessage;
import com.tree.gdhealth.vo.ChatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {

	private final ChatMapper chatMapper;

	public List<Map<String, Object>> getRoomList() {

		List<Map<String, Object>> chatRoomList = chatMapper.roomList();
		// 디버깅
		log.debug("방 리스트 : " + chatRoomList.toString());

		return chatRoomList;

	}

	public List<ChatMessage> getChatList(int chatRoomNo) {

		List<ChatMessage> chatList = chatMapper.chatList(chatRoomNo);
		// 디버깅
		log.debug("채팅 기록 : " + chatList);

		return chatList;
	}

	public int getRoomNo(String customerId) {

		int getRoomNo = chatMapper.getRoomNo(customerId);
		// 디버깅
		log.debug("방 번호 : " + getRoomNo);

		return getRoomNo;
	}

	public boolean isRoomExists(int customerNo) {

		boolean isRoomExists = chatMapper.isRoomExists(customerNo);
		// 디버깅
		log.debug("방이 존재하는지 여부 : " + isRoomExists);

		return isRoomExists;
	}

	public int insertRoom(ChatRoom chatRoom) {

		int result = chatMapper.insertRoom(chatRoom);
		// 디버깅
		log.debug("방 추가(성공:1) : " + result);

		return result;

	}

}
