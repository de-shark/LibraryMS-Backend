package me.deshark.lms.domain.repository.comment;

import me.deshark.lms.domain.model.comment.BookComment;

public interface BookCommentRepository {
    void save(BookComment bookComment);
}
