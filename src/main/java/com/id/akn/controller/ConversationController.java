package com.id.akn.controller;

import com.id.akn.exception.UserException;
import com.id.akn.model.Conversation;
import com.id.akn.model.Message;
import com.id.akn.model.User;
import com.id.akn.service.ChatService;
import com.id.akn.service.ConversationService;
import com.id.akn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@AllArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;
    private final ChatService chatService;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<Conversation>> getConversationsForCurrentUser(
            @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);
        List<Conversation> conversations = conversationService.getConversationsForUser(user.getId());
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/with/{recipientId}")
    public ResponseEntity<Conversation> getOrCreateConversation(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long recipientId) throws UserException {

        User sender = userService.findUserProfileByJwt(jwt);
        User recipient = userService.findUserById(recipientId);

        Long userId;
        Long adminId;

        if (sender.getRole().equals(User.Role.ADMIN) && recipient.getRole().equals(User.Role.USER)) {
            adminId = sender.getId();
            userId = recipient.getId();
        } else if (sender.getRole().equals(User.Role.USER) && recipient.getRole().equals(User.Role.ADMIN)) {
            adminId = recipient.getId();
            userId = sender.getId();
        } else {
            throw new UserException("Không thể tạo cuộc hội thoại giữa " + sender.getRole() + " và " + recipient.getRole());
        }

        Conversation conversation = conversationService.getOrCreateConversation(userId, adminId);
        return ResponseEntity.ok(conversation);
    }

    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<List<Message>> getMessagesForConversation(
            @PathVariable Long conversationId) {

        List<Message> messages = chatService.getMessagesForConversation(conversationId);
        return ResponseEntity.ok(messages);
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history