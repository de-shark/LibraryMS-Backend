package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class BookCommentDO {
    private UUID commentId;
    private String isbn;
    private UUID userId;
    private UUID parentCommentId;
    private String content;
    private int rating;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private boolean isDeleted;
}
