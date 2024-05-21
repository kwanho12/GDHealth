package com.tree.gdhealth.customer.chat;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.domain.ChatMessage;
import com.tree.gdhealth.domain.ChatRoom;

import lombok.RequiredArgsConstructor;

/**
 * 회원의 채팅 service
 * 
 * @author 진관호
 */
@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {

	private final ChatMapper chatMapper;

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
	 * 채팅방 번호를 리턴합니다.
	 * 
	 * @param customerNo 회원 번호
	 * @return 채팅방 번호
	 */
	public int getRoomNo(int customerNo) {
		return chatMapper.selectRoomNo(customerNo);
	}

	/**
	 * 회원의 채팅방이 이미 존재한다면 true를 리턴합니다.
	 * 
	 * @param 회원 번호
	 * @return 채팅방이 이미 존재한다면 true, 그렇지 않다면 false
	 */
	public boolean checkRoomExists(int customerNo) {
		return chatMapper.selectIsRoomExists(customerNo);
	}

	/**
	 * 채팅방을 추가하고 1을 리턴합니다.
	 * 
	 * @param chatRoom 객체
	 * @return 채팅방이 추가되었다면 1, 그렇지 않다면 0
	 */
	public int insertRoom(ChatRoom chatRoom) {
		return chatMapper.insertRoom(chatRoom);
	}
	
	/**
	 * 채팅 메시지를 저장합니다.
	 * 
	 * @param chatMessage 메시지를 DB에 저장하기 위한 객체
	 */
	public void saveMessage(ChatMessage chatMessage) {
		chatMapper.insertMessage(chatMessage);
	}

}
