package com.id.akn.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {

    private Long conversationId;

    private String content;
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history