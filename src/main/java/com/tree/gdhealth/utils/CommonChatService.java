package com.tree.gdhealth.utils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Queue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.customer.chat.ChatMapper;
import com.tree.gdhealth.domain.ChatMessage;
import com.tree.gdhealth.headoffice.chat.AdministratorChatMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonChatService {

	private final ChatMapper customerChatMapper;
	private final AdministratorChatMapper administratorChatMapper;

	private final Queue<ChatMessage> messageBuffer = new ConcurrentLinkedQueue<>();

	public void addMessageToBuffer(ChatMessage chatMessage) {
		messageBuffer.add(chatMessage);
	}

	public void bufferMessage(ChatMessage chatMessage) {
		messageBuffer.offer(chatMessage);
	}

	@Scheduled(fixedRate = 60000) // 1분마다 실행
	public void saveBufferedMessages() {
		List<ChatMessage> messagesToSave = messageBuffer.stream().collect(Collectors.toList());
		messageBuffer.clear();
		saveMessages(messagesToSave);
	}

	private void saveMessages(List<ChatMessage> messages) {
		for (ChatMessage chatMessage : messages) {
			if (chatMessage.getCustomerNo() != null) {
				customerChatMapper.insertMessage(chatMessage);
			} else {
				administratorChatMapper.insertMessage(chatMessage);
			}
		}
	}

}
