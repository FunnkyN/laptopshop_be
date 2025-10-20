package com.id.akn.serviceimpl;

import com.id.akn.exception.UserException;
import com.id.akn.model.Conversation;
import com.id.akn.model.User;
import com.id.akn.repository.ConversationRepository;
import com.id.akn.service.ConversationService;
import com.id.akn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Conversation getOrCreateConversation(Long userId, Long adminId) throws UserException {
        Optional<Conversation> existingConversation = conversationRepository.findByUserIdAndAdminId(userId, adminId);
        if (existingConversation.isPresent()) {
            return existingConversation.get();
        }

        User user = userService.findUserById(userId);
        User admin = userService.findUserById(adminId);

        if (user.getRole().equals(User.Role.ADMIN)) {
            throw new UserException("Người dùng (user) không thể là Admin trong cuộc hội thoại này.");
        }
        if (!admin.getRole().equals(User.Role.ADMIN)) {
            throw new UserException("Người nhận (admin) không có vai trò ADMIN.");
        }

        Conversation newConversation = new Conversation();
        newConversation.setUser(user);
        newConversation.setAdmin(admin);
        newConversation.setLastMessageAt(LocalDateTime.now());

        return conversationRepository.save(newConversation);
    }

    @Override
    public List<Conversation> getConversationsForUser(Long userId) throws UserException {
        User user = userService.findUserById(userId);

        if (user.getRole().equals(User.Role.ADMIN)) {
            return conversationRepository.findByAdminIdOrderByLastMessageAtDesc(userId);
        } else {
            return conversationRepository.findByUserIdOrderByLastMessageAtDesc(userId);
        }
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history