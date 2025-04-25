package me.deshark.lms.infrastructure.repository.comment;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.comment.BookComment;
import me.deshark.lms.domain.repository.comment.BookCommentRepository;
import me.deshark.lms.infrastructure.converter.BookCommentConverter;
import me.deshark.lms.infrastructure.entity.BookCommentDO;
import me.deshark.lms.infrastructure.mapper.BookCommentMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookCommentRepoImpl implements BookCommentRepository {

    private final BookCommentMapper bookCommentMapper;

    @Override
    public List<BookComment> findByIsbn(String isbn) {
        return bookCommentMapper.findByIsbn(isbn)
                .stream()
                .map(BookCommentConverter.INSTANCE::doToModel)
                .collect(Collectors.toList());
    }

    @Override
    public void save(BookComment bookComment) {
        BookCommentDO bookCommentDO = BookCommentConverter.INSTANCE.modelToDO(bookComment);
        bookCommentMapper.save(bookCommentDO);
    }
}
