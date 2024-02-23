package com.tree.gdhealth.headoffice.chat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.vo.ChatMessage;

@Mapper
public interface AdministratorChatMapper {

	List<Map<String, Object>> selectRoomList();

	List<ChatMessage> selectChatList(int chatRoomNo);

	int selectRoomNo(String customerId);

	int insertMessage(ChatMessage chatMessage);

}
