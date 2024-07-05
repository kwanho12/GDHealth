package com.tree.gdhealth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
	private int chatMessageNo;
	private int chatRoomNo;
	private Integer customerNo;
	private Integer employeeNo;
	private String messageContent;
	private String createdate;
}
