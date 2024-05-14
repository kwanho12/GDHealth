package com.tree.gdhealth.domain;

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
