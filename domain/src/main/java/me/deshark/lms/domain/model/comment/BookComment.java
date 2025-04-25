package me.deshark.lms.domain.model.comment;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class BookComment {
    private UUID commentId;
    private Isbn isbn;
    private UUID userId;
    private String content;
    private int rating;
}
