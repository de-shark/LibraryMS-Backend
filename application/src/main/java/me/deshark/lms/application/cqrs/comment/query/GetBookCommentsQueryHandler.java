package me.deshark.lms.application.cqrs.comment.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.converter.BookCommentConverter;
import me.deshark.lms.application.cqrs.core.QueryHandler;
import me.deshark.lms.application.info.BookCommentInfo;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.comment.BookCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetBookCommentsQueryHandler implements QueryHandler<GetBookCommentsQuery, List<BookCommentInfo>> {

    private final BookCommentRepository bookCommentRepository;
    private final BookCommentConverter bookCommentConverter;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<BookCommentInfo>> handle(GetBookCommentsQuery query) {
        Isbn isbn = new Isbn(query.getIsbn());

        List<BookCommentInfo> comments = bookCommentRepository.findByIsbn(isbn.toString())
                .stream()
                .map(bookCommentConverter::modelToInfo)
                .collect(Collectors.toList());

        return Optional.of(comments);
    }
}