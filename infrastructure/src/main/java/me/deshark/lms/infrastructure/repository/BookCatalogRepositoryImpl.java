package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.exception.BookNotFoundException;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookCatalogRepository;
import me.deshark.lms.infrastructure.entity.BookCatalogDO;
import me.deshark.lms.infrastructure.mapper.BookCatalogMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class BookCatalogRepositoryImpl implements BookCatalogRepository {

    private final BookCatalogMapper bookCatalogMapper;

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookCatalogMapper.existsByIsbn(isbn);
    }

    @Override
    public BookCatalog findByIsbn(Isbn isbn) {
        Optional<BookCatalogDO> bookCatalogDO = bookCatalogMapper.findByIsbn(isbn.toString());
        if (bookCatalogDO.isEmpty()) {
            throw new BookNotFoundException("未找到ISBN为" + isbn + "的图书");
        }
        return BookCatalog.builder()
                .isbn(isbn)
                .title(bookCatalogDO.get().getTitle())
                .author(bookCatalogDO.get().getAuthor())
                .build();
    }

    @Override
    public void save(BookCatalog bookCatalog) {
        BookCatalogDO bookCatalogDO = BookCatalogDO.builder()
                .isbn(bookCatalog.getIsbn().toString())
                .title(bookCatalog.getTitle())
                .author(bookCatalog.getAuthor())
                .build();
        bookCatalogMapper.insert(bookCatalogDO);
    }

    @Override
    public void delete(Isbn isbn) {
        bookCatalogMapper.delete(isbn.toString());
    }

    @Override
    public void update(BookCatalog bookCatalog) {
        BookCatalogDO bookCatalogDO = BookCatalogDO.builder()
                .isbn(bookCatalog.getIsbn().toString())
                .title(bookCatalog.getTitle())
                .author(bookCatalog.getAuthor())
                .publisher(bookCatalog.getPublisher())
                .publishYear(bookCatalog.getPublishedYear())
                .description(bookCatalog.getDescription())
                .build();
        bookCatalogMapper.update(bookCatalogDO);
    }
}
