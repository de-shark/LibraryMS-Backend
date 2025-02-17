package me.deshark.lms.domain.repository;

import me.deshark.lms.domain.model.catalog.entity.BookInfo;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

/**
 * @author DE_SHARK
 * @date 2025/2/16 21:25
 */
public interface BookRepository {
    BookInfo findByIsbn(Isbn isbn);
    int countAvailableCopies(Isbn isbn);
}
