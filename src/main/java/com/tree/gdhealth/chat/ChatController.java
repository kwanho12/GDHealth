package com.tree.gdhealth.chat;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.vo.ChatMessage;
import com.tree.gdhealth.vo.ChatRoom;

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
public class ChatController {

	private final ChatService chatService;

	// 채팅방(고객)
	@Auth(AUTHORITY = Authority.CUSTOMER_ONLY)
	@GetMapping("/customerRoom")
	public String accessChatRoom(Model model, ChatRoom chatRoom, @SessionAttribute("customerNo") int customerNo,
			@SessionAttribute("customerId") String customerId) {
		// 해당 고객에 대한 채팅방이 이미 존재하는지 확인
		boolean isRoomExists = chatService.getIsRoomExists(customerNo);
		if (!isRoomExists) { // 방이 아직 존재하지 않다면 방을 추가

			chatRoom.setCustomerNo(customerNo);
			int insertRoom = chatService.insertRoom(chatRoom);
			log.debug("방 추가(성공:1) : " + insertRoom);

			model.addAttribute("roomNo", chatRoom.getChatRoomNo());
		}

		model.addAttribute("status", "customer");

		int roomNo = chatService.getRoomNo(customerId);
		model.addAttribute("roomNo", roomNo);

		List<ChatMessage> messageList = chatService.getChatList(roomNo);
		model.addAttribute("messageList", messageList);
		log.debug("messageList : " + messageList.toString());

		return "chat/chat";

	}

}
