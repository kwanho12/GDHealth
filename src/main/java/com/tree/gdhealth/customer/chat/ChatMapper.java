package com.tree.gdhealth.customer.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.domain.ChatMessage;
import com.tree.gdhealth.domain.ChatRoom;

/**
 * @author 진관호
 * */
@Mapper
public interface ChatMapper {

	List<ChatMessage> selectChatList(int chatRoomNo);
	
	int selectRoomNo(int customerNo);
	
	int insertMessage(ChatMessage chatMessage);

	boolean selectIsRoomExists(int customerNo);

	int insertRoom(ChatRoom chatRoom);


}
