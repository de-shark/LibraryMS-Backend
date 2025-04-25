package me.deshark.lms.application.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCommentInfo {
    private UUID commentId;
    private String isbn;
    private UUID userId;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
}