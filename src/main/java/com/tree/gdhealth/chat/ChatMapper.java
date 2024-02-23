package com.tree.gdhealth.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.vo.ChatMessage;
import com.tree.gdhealth.vo.ChatRoom;

@Mapper
public interface ChatMapper {

	List<ChatMessage> selectChatList(int chatRoomNo);

	boolean selectIsRoomExists(int customerNo);

	int selectRoomNo(String customerId);

	int insertRoom(ChatRoom chatRoom);

	int insertMessage(ChatMessage chatMessage);

}
