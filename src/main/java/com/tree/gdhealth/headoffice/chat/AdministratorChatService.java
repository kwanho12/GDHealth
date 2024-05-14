package com.tree.gdhealth.headoffice.chat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.domain.ChatMessage;

import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequiredArgsConstructor
@Transactional
@Service
public class AdministratorChatService {

	private final AdministratorChatMapper chatMapper;

	/**
	 * 채팅방 목록을 리턴합니다.
	 * 
	 * @return 채팅방 목록
	 */
	public List<Map<String, Object>> getRoomList() {
		return chatMapper.selectRoomList();
	}

	/**
	 * 채팅 메시지들을 리턴합니다.
	 * 
	 * @param chatRoomNo 채팅방 번호
	 * @return 채팅 메시지
	 */
	public List<ChatMessage> getChatList(int chatRoomNo) {
		return chatMapper.selectChatList(chatRoomNo);
	}

	/**
	 * 특정한 회원의 채팅방 번호를 리턴합니다.
	 * 
	 * @param customerId 고객id
	 * @return 방 번호
	 */
	public int getRoomNo(String customerId) {
		return chatMapper.selectRoomNo(customerId);
	}

}
