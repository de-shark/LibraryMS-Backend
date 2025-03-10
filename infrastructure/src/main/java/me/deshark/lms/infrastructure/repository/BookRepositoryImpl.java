package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.exception.BookNotFoundException;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookRepository;
import me.deshark.lms.infrastructure.entity.BookDO;
import me.deshark.lms.infrastructure.mapper.BookMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final BookMapper bookMapper;

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookMapper.existsByIsbn(isbn);
    }

    @Override
    public BookMetadata findByIsbn(Isbn isbn) {
        Optional<BookDO> bookCatalogDO = bookMapper.findByIsbn(isbn.toString());
        if (bookCatalogDO.isEmpty()) {
            throw new BookNotFoundException("未找到ISBN为" + isbn + "的图书");
        }
        return BookMetadata.builder()
                .isbn(isbn)
                .title(bookCatalogDO.get().getTitle())
                .author(bookCatalogDO.get().getAuthor())
                .build();
    }

    @Override
    public void save(BookMetadata bookMetadata) {
        BookDO bookDO = BookDO.builder()
                .isbn(bookMetadata.getIsbn().toString())
                .title(bookMetadata.getTitle())
                .author(bookMetadata.getAuthor())
                .build();
        bookMapper.insert(bookDO);
    }

    @Override
    public void delete(Isbn isbn) {
        bookMapper.delete(isbn.toString());
    }
}
