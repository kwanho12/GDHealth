package com.tree.gdhealth.headoffice.chat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tree.gdhealth.domain.ChatMessage;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;

import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequestMapping("/chat")
@RequiredArgsConstructor
@Controller
public class AdministratorChatController {

	private final AdministratorChatService chatService;

	/**
	 * 회원들의 채팅방 목록 페이지로 이동합니다.
	 * 
	 * @return 본사 직원의 채팅방 목록 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/roomList")
	public String getRoomList(Model model) {
		model.addAttribute("roomList", chatService.getRoomList());
		return "chat/roomList";
	}

	/**
	 * 본사 직원의 채팅방 페이지로 이동합니다.
	 * 
	 * @param customerId 해당 고객의 방 번호를 알기 위해 사용하는 고객id
	 * @return 채팅방 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/headofficeRoom")
	public String accessChatRoom(Model model, @RequestParam String customerId) {

		List<ChatMessage> messageList = chatService.getChatList(chatService.getRoomNo(customerId));

		model.addAttribute("customerId", customerId);
		model.addAttribute("status", "employee");
		model.addAttribute("roomNo", chatService.getRoomNo(customerId));
		model.addAttribute("messageList", messageList);

		return "chat/chatRoom";
	}

}
