package me.deshark.lms.domain.repository;

import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 21:25
 */
public interface BookRepository {

    boolean existsByIsbn(String isbn);

    BookCatalog findByIsbn(Isbn isbn);

    int countAvailableCopies(Isbn isbn);

    // 查找可用的图书副本
    BookCopy findAvailableBookCopy(Isbn isbn);

    BookCopy findBookCopy(UUID bookCopyId);

    // 保存
    void saveBookCatalog(BookCatalog bookCatalog);

    void saveBookCopy(BookCopy bookCopy);

    // delete
    void deleteBookCatalog(Isbn isbn);

    void deleteBookCopy(UUID bookCopyId);
}
