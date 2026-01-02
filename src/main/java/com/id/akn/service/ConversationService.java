package com.id.akn.service;

import java.util.List;

import com.id.akn.exception.UserException;
import com.id.akn.model.Conversation;

public interface ConversationService {

    Conversation getOrCreateConversation(Long userId, Long adminId) throws UserException;

    List<Conversation> getConversationsForUser(Long userId) throws UserException;

}
