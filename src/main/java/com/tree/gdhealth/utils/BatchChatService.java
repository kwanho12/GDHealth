package com.tree.gdhealth.utils;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tree.gdhealth.customer.chat.ChatMapper;
import com.tree.gdhealth.domain.ChatMessage;
import com.tree.gdhealth.headoffice.chat.AdministratorChatMapper;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BatchChatService {
	
	private final ChatMapper customerChatMapper;
	private final AdministratorChatMapper administratorChatMapper;
	
	private final List<ChatMessage> messageBuffer = new CopyOnWriteArrayList<>();
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	public void addMessageToBuffer(ChatMessage chatMessage) {
		messageBuffer.add(chatMessage);
	}

	@Scheduled(fixedRate = 5000) 
	public void saveMessagesBatch() {
		if (!messageBuffer.isEmpty()) {
			executorService.submit(() -> {
				try {
					List<ChatMessage> messagesToSave = new ArrayList<>(messageBuffer);
					messageBuffer.clear();
					messagesToSave.forEach(message -> {
						if (message.getCustomerNo() != null) {
							customerChatMapper.insertMessage(message);
						} else {
							administratorChatMapper.insertMessage(message);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	@PostConstruct
	public void init() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				saveRemainingMessages();
				executorService.shutdown();
				executorService.awaitTermination(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}));
	}

	@PreDestroy
	public void destroy() {
		saveRemainingMessages();
		executorService.shutdown();
	}

	private void saveRemainingMessages() {
		if (!messageBuffer.isEmpty()) {
			List<ChatMessage> messagesToSave = new ArrayList<>(messageBuffer);
			messageBuffer.clear();
			messagesToSave.forEach(message -> {
				if (message.getCustomerNo() != null) {
					customerChatMapper.insertMessage(message);
				} else {
					administratorChatMapper.insertMessage(message);
				}
			});
		}
	}
}
