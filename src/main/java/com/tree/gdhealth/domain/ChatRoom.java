package com.tree.gdhealth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoom {
	private int chatRoomNo;
	private int customerNo;
	private String createdate;
}
