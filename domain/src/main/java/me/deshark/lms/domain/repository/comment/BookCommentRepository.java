package me.deshark.lms.domain.repository.comment;

import me.deshark.lms.domain.model.comment.BookComment;

import java.util.List;

public interface BookCommentRepository {
    List<BookComment> findByIsbn(String isbn);
    void save(BookComment bookComment);
}
