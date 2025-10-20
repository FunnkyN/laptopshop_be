package com.id.akn.repository;

import com.id.akn.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversationIdOrderByTimestampAsc(Long conversationId);
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history