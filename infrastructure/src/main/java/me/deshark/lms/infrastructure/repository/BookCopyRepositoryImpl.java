package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.catalog.BookCopyRepository;
import me.deshark.lms.infrastructure.mapper.BookCopyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/26 19:01
 */
@Repository
@RequiredArgsConstructor
public class BookCopyRepositoryImpl implements BookCopyRepository {

    private final BookCopyMapper bookCopyMapper;

    @Override
    public int countAvailableCopies(Isbn isbn) {
        return 0;
    }

    @Override
    public BookCopy findAvailableBookCopy(Isbn isbn) {
        return bookCopyMapper.findAvailableByIsbn(isbn.toString())
                .flatMap(bookCopyMapper::toDomain)
                .orElseThrow(() -> new IllegalStateException("找不到可用的书籍副本 ISBN: " + isbn));
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

    @Override
    public void updateBookCopyStatus(BookCopy bookCopy) {

    }

    @Override
    public void saveAllCopies(List<BookCopy> copies) {

    }
}
