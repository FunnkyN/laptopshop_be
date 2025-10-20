package com.id.akn.serviceimpl;

import com.id.akn.exception.UserException;
import com.id.akn.model.Conversation;
import com.id.akn.model.Message;
import com.id.akn.model.User;
import com.id.akn.repository.ConversationRepository;
import com.id.akn.repository.MessageRepository;
import com.id.akn.service.ChatService;
import com.id.akn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Message sendMessage(Long senderId, Long conversationId, String content) throws UserException {

        User sender = userService.findUserById(senderId);

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc hội thoại (Conversation) với ID: " + conversationId));

        Message newMessage = new Message();
        newMessage.setConversation(conversation);
        newMessage.setSender(sender);
        newMessage.setContent(content);
        newMessage.setTimestamp(LocalDateTime.now());
        newMessage.setStatus(Message.MessageStatus.SENT);

        Message savedMessage = messageRepository.save(newMessage);

        conversation.setLastMessageAt(savedMessage.getTimestamp());
        conversationRepository.save(conversation);

        return savedMessage;
    }

    @Override
    public List<Message> getMessagesForConversation(Long conversationId) {
        return messageRepository.findByConversationIdOrderByTimestampAsc(conversationId);
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history
//  Thay đổi nội dung để cập nhật git history