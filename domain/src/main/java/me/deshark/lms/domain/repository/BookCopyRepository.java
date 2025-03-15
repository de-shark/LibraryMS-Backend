package me.deshark.lms.domain.repository;

import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 21:25
 */
public interface BookCopyRepository {

    int countAvailableCopies(Isbn isbn);

    BookCopy findAvailableBookCopy(Isbn isbn);

    BookCopy findBookCopy(UUID bookCopyId);

    void saveBookCopy(BookCopy bookCopy);

    void deleteBookCopy(UUID bookCopyId);

    void updateBookCopyStatus(BookCopy bookCopy);
}
