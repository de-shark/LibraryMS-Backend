package me.deshark.lms.domain.repository;

import me.deshark.lms.domain.model.catalog.entity.Book;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

/**
 * @author DE_SHARK
 * @date 2025/2/16 21:25
 */
public interface BookRepository {
    Book findByIsbn(Isbn isbn);
    int countAvailableCopies(Isbn isbn);
}
