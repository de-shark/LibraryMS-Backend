package me.deshark.lms.interfaces.dto.comment;

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
public class BookCommentResponse {
    private UUID commentId;
    private UUID userId;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
}