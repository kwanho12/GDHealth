package com.tree.gdhealth.dto;

import lombok.Data;

@Data
public class ChatMessage {
	
	int chatMessageNo;
	int chatRoomNo;
	Integer customerNo;
	Integer employeeNo;
	String messageContent;
	String createdate;

}
