package com.tree.gdhealth.headoffice.chat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.vo.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * 
 * */

@Slf4j
@RequestMapping("/chat")
@RequiredArgsConstructor
@Controller
public class AdministratorChatController {

	private final AdministratorChatService chatService;

	// 방 목록
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/roomList")
	public String getRoomList(Model model) {

		List<Map<String, Object>> roomList = chatService.getRoomList();
		model.addAttribute("roomList", roomList);

		return "chat/room";
	}

	// 채팅창(본사)
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/headofficeRoom")
	public String accessChatRoom(Model model, String customerId) {

		model.addAttribute("customerId", customerId);
		model.addAttribute("status", "employee");

		int roomNo = chatService.getRoomNo(customerId);
		model.addAttribute("roomNo", roomNo);

		List<ChatMessage> messageList = chatService.getChatList(roomNo);
		model.addAttribute("messageList", messageList);
		log.debug("messageList : " + messageList.toString());

		return "chat/chat";
	}

}
