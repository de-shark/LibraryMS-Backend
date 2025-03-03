package me.deshark.lms.application.cqrs.book.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class GetBookByIsbnQueryHandler {

    private final BookRepository bookRepository;

    public BookInfo handle(GetBookByIsbnQuery query) {
        Isbn isbn = new Isbn(query.isbn());

        BookMetadata bookMetadata = bookRepository.findByIsbn(isbn);

        return BookInfo.builder()
                .isbn(bookMetadata.getIsbn().toString())
                .title(bookMetadata.getTitle())
                .author(bookMetadata.getAuthor())
//            .publisher(bookCatalog.getPublisher())
//            .publishYear(bookCatalog.getPublishYear())
//            .description(bookCatalog.getDescription())
//            .availableCopies(bookCatalog.getCountAvailableCopies())
                .build();
    }
}
