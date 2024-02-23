package com.tree.gdhealth.customer.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.ChatMessage;
import com.tree.gdhealth.dto.ChatRoom;

/**
 * @author 진관호
 * */
@Mapper
public interface ChatMapper {

	List<ChatMessage> selectChatList(int chatRoomNo);

	boolean selectIsRoomExists(int customerNo);

	int selectRoomNo(int customerNo);

	int insertRoom(ChatRoom chatRoom);

	int insertMessage(ChatMessage chatMessage);

}
