package com.tree.gdhealth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessage {
	int chatMessageNo;
	int chatRoomNo;
	Integer customerNo;
	Integer employeeNo;
	String messageContent;
	String createdate;
}
