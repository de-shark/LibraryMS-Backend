package me.deshark.lms.application.cqrs.book.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookQueryRepository;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class GetBookByIsbnQueryHandler {

    private final BookQueryRepository bookQueryRepository;

    public BookInfo handle(GetBookByIsbnQuery query) {
        Isbn isbn = new Isbn(query.isbn());

        bookQueryRepository.findBookByIsbn(isbn);

        return BookInfo.builder().isbn(isbn.toString()).build();

    }
}
