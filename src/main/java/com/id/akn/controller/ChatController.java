package com.id.akn.controller;

import com.id.akn.exception.UserException;
import com.id.akn.model.Conversation;
import com.id.akn.model.Message;
import com.id.akn.model.User;
import com.id.akn.request.ChatMessageDTO;
import com.id.akn.service.ChatService;
import com.id.akn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO, Principal principal) throws UserException {

        String senderEmail = principal.getName();
        User sender = userService.findUserByEmail(senderEmail);

        Message savedMessage = chatService.sendMessage(
                sender.getId(),
                chatMessageDTO.getConversationId(),
                chatMessageDTO.getContent()
        );

        Conversation conversation = savedMessage.getConversation();
        User recipient;

        if (sender.getId().equals(conversation.getUser().getId())) {
            recipient = conversation.getAdmin();
        }
        else {
            recipient = conversation.getUser();
        }

        simpMessagingTemplate.convertAndSendToUser(
                recipient.getEmail(),
                "/queue/messages",
                savedMessage
        );

        simpMessagingTemplate.convertAndSendToUser(
                sender.getEmail(),
                "/queue/messages",
                savedMessage
        );
    }
}

//  Thay đổi nội dung để cập nhật git history