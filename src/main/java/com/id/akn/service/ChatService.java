package com.id.akn.service;

import com.id.akn.exception.UserException;
import com.id.akn.model.Message;

import java.util.List;

public interface ChatService {

    Message sendMessage(Long senderId, Long conversationId, String content) throws UserException;

    List<Message> getMessagesForConversation(Long conversationId);
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history