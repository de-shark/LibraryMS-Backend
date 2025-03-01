package me.deshark.lms.domain.repository;

import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

/**
 * @author DE_SHARK
 */
public interface BookCatalogRepository {

    boolean existsByIsbn(String isbn);

    BookCatalog findByIsbn(Isbn isbn);

    void saveBookCatalog(BookCatalog bookCatalog);

    void deleteBookCatalog(Isbn isbn);
}
