package me.deshark.lms.domain.repository;

import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

/**
 * @author DE_SHARK
 */
public interface BookRepository {

    boolean existsByIsbn(String isbn);

    BookMetadata findByIsbn(Isbn isbn);

    void save(BookMetadata bookMetadata);

    void delete(Isbn isbn);

    Page<BookMetadata> searchBooks(String keyword, Pageable pageable);
}
