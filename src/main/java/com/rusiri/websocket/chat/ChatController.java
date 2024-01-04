package com.rusiri.websocket.chat;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(
        @Payload ChatMessage chatMessage
    ) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
            chatMessage.getRecipientId(), 
        "/queue/messages", 
        ChatNotification.builder()
            .id(savedMsg.getId())
            .senderId(savedMsg.getSenderId())
            .recipientId(savedMsg.getRecipientId())
            .content(savedMsg.getContent())
            .build());
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
        @PathVariable("senderId") String senderId,
        @PathVariable("recipientId") String recipientId        
    ) {

        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
    
}
