package com.tree.gdhealth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
	int chatMessageNo;
	int chatRoomNo;
	Integer customerNo;
	Integer employeeNo;
	String messageContent;
	String createdate;
}
