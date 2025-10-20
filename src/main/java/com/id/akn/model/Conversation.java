package com.id.akn.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"addresses", "ratings", "reviews", "password"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonIgnoreProperties({"addresses", "ratings", "reviews", "password"})
    private User admin;

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history
//  Thay đổi nội dung để cập nhật git history