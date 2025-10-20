package com.id.akn.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String thumbnail;
    private String shortDescription;
    private String authorName;
    private Long authorId;
    private LocalDateTime createdAt;

    public boolean isValid() {
        return title != null && !title.isEmpty() && content != null && !content.isEmpty();
    }
}
