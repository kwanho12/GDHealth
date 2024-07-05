package com.tree.gdhealth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
	
	private int chatMessageNo;
	private int chatRoomNo;
	private Integer customerNo;
	private Integer employeeNo;
	private String messageContent;
	private String createdate;
}
