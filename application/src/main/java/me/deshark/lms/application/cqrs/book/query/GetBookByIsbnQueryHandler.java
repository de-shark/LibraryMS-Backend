package me.deshark.lms.application.cqrs.book.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.converter.BookMetadataConverter;
import me.deshark.lms.application.cqrs.core.QueryHandler;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.catalog.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class GetBookByIsbnQueryHandler implements QueryHandler<GetBookByIsbnQuery, BookInfo> {

    private final BookRepository bookRepository;

    public Optional<BookInfo> handle(GetBookByIsbnQuery query) {
        Isbn isbn = new Isbn(query.getIsbn());

        return bookRepository.findByIsbn(isbn).map(BookMetadataConverter.INSTANCE::entityToInfo);
    }
}
