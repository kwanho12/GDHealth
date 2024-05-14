package com.tree.gdhealth.customer.chat;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.tree.gdhealth.domain.ChatMessage;
import com.tree.gdhealth.domain.ChatRoom;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;

import lombok.RequiredArgsConstructor;

/**
 * 회원의 채팅 controller
 * 
 * @author 진관호
 */
@RequestMapping("/chat")
@RequiredArgsConstructor
@Controller
public class ChatController {

	private final ChatService chatService;

	/**
	 * 회원의 채팅방 페이지로 이동합니다.
	 * 
	 * @param chatRoom   채팅방을 추가하기 위해 사용하는 chatRoom 객체
	 * @param customerNo 방이 이미 존재하는지 확인하기 위해 사용하는 회원 번호
	 * @return 채팅방 페이지
	 * @apiNote 채팅방이 존재하지 않는다면 채팅방을 추가하고 존재한다면 채팅방의 채팅 목록을 가져 옵니다.
	 */
	@Auth(AUTHORITY = Authority.CUSTOMER_ONLY)
	@GetMapping("/customerRoom")
	public String accessChatRoom(Model model, @SessionAttribute("customerNo") int customerNo) {

		int roomNo;
		boolean isRoomExists = chatService.checkRoomExists(customerNo);
		
		if (!isRoomExists) {
			ChatRoom chatRoom = new ChatRoom();
			chatRoom.setCustomerNo(customerNo);
			chatService.insertRoom(chatRoom);
			roomNo = chatRoom.getChatRoomNo();
		} else {
			roomNo = chatService.getRoomNo(customerNo);
			List<ChatMessage> messageList = chatService.getChatList(roomNo);
			model.addAttribute("messageList", messageList);
		}

		model.addAttribute("status", "customer");
		model.addAttribute("roomNo", roomNo);

		return "chat/chatRoom";
	}

}
