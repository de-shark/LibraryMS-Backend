package me.deshark.lms.application.cqrs.book.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookCatalogRepository;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class GetBookByIsbnQueryHandler {

    private final BookCatalogRepository bookCatalogRepository;

    public BookInfo handle(GetBookByIsbnQuery query) {
        Isbn isbn = new Isbn(query.isbn());

        BookCatalog bookCatalog = bookCatalogRepository.findByIsbn(isbn);

        return BookInfo.builder()
                .isbn(bookCatalog.getIsbn().toString())
                .title(bookCatalog.getTitle())
                .author(bookCatalog.getAuthor())
//            .publisher(bookCatalog.getPublisher())
//            .publishYear(bookCatalog.getPublishYear())
//            .description(bookCatalog.getDescription())
//            .availableCopies(bookCatalog.getCountAvailableCopies())
                .build();
    }
}
