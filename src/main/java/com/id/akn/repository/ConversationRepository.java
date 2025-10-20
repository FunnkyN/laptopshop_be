package com.id.akn.repository;

import com.id.akn.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByUserIdAndAdminId(Long userId, Long adminId);

    List<Conversation> findByUserIdOrderByLastMessageAtDesc(Long userId);

    List<Conversation> findByAdminIdOrderByLastMessageAtDesc(Long adminId);
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history