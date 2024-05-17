package com.tree.gdhealth.headoffice.chat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.domain.ChatMessage;

/**
 * @author 진관호
 */
@Mapper
public interface AdministratorChatMapper {
	
	List<ChatMessage> selectChatList(int chatRoomNo);
	
	int selectRoomNo(String customerId);
	
	int insertMessage(ChatMessage chatMessage);

	List<Map<String, Object>> selectRoomList();




}
