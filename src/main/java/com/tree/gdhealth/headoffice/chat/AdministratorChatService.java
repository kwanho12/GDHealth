package com.tree.gdhealth.headoffice.chat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.vo.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AdministratorChatService {

	private final AdministratorChatMapper chatMapper;

	public List<Map<String, Object>> getRoomList() {

		List<Map<String, Object>> chatRoomList = chatMapper.selectRoomList();
		// 디버깅
		log.debug("방 리스트 : " + chatRoomList.toString());

		return chatRoomList;

	}

	public List<ChatMessage> getChatList(int chatRoomNo) {

		List<ChatMessage> chatList = chatMapper.selectChatList(chatRoomNo);
		// 디버깅
		log.debug("채팅 기록 : " + chatList);

		return chatList;
	}

	public int getRoomNo(String customerId) {

		int getRoomNo = chatMapper.selectRoomNo(customerId);
		// 디버깅
		log.debug("방 번호 : " + getRoomNo);

		return getRoomNo;
	}

}
