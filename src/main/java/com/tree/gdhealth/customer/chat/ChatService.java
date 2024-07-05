package com.tree.gdhealth.customer.chat;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.domain.ChatMessage;
import com.tree.gdhealth.domain.ChatRoom;
import com.tree.gdhealth.dto.ChatMessageDto;
import com.tree.gdhealth.dto.ChatRoomDto;

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
	public int insertRoom(ChatRoomDto chatRoomDto) {
		ChatRoom chatRoom = ChatRoom.builder()
								.customerNo(chatRoomDto.getCustomerNo())
								.build();
		chatMapper.insertRoom(chatRoom);
		return chatRoom.getChatRoomNo();
	}
	
	/**
	 * 채팅 메시지를 데이터베이스에 저장합니다.
	 *
	 * @param chatMessageDto 채팅 메시지의 세부 정보를 포함하는 데이터 전송 객체
	 */
	public void saveMessage(ChatMessageDto chatMessageDto) {
		ChatMessage chatMessage = ChatMessage.builder()
									.chatRoomNo(chatMessageDto.getChatRoomNo())
									.customerNo(chatMessageDto.getCustomerNo())
									.employeeNo(chatMessageDto.getEmployeeNo())
									.messageContent(chatMessageDto.getMessageContent())
									.build();
		chatMapper.insertMessage(chatMessage);
	}

}
