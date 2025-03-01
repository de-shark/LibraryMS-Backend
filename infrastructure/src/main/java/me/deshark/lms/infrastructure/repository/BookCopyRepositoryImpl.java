package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookCopyRepository;
import me.deshark.lms.infrastructure.mapper.BookCopyMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/26 19:01
 */
@Repository
@RequiredArgsConstructor
public class BookCopyRepositoryImpl implements BookCopyRepository {

    private final BookCopyMapper bookCatalogMapper;

    @Override
    public int countAvailableCopies(Isbn isbn) {
        return 0;
    }

    @Override
    public BookCopy findAvailableBookCopy(Isbn isbn) {
        return null;
    }

    @Override
    public BookCopy findBookCopy(UUID bookCopyId) {
        return null;
    }

    @Override
    public void saveBookCopy(BookCopy bookCopy) {

    }

    @Override
    public void deleteBookCopy(UUID bookCopyId) {

    }
}
