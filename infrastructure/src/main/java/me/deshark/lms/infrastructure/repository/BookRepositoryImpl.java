package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.exception.BookNotFoundException;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookRepository;
import me.deshark.lms.infrastructure.entity.BookInfoDO;
import me.deshark.lms.infrastructure.mapper.BookCopyMapper;
import me.deshark.lms.infrastructure.mapper.bookCatalogMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/26 19:01
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookRepositoryImpl implements BookRepository {

    private final bookCatalogMapper bookCatalogMapper;
    private final BookCopyMapper bookCopyMapper;

    @Override
    public BookCatalog findByIsbn(Isbn isbn) {
        Optional<BookInfoDO> bookInfoDO = bookCatalogMapper.findByIsbn(isbn.getIsbn());
        if (bookInfoDO.isEmpty()) {
            throw new BookNotFoundException("未找到ISBN为" + isbn + "的图书");
        }
        return bookCatalogMapper.toDomainEntity(bookInfoDO.get());
    }

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
    public void saveBookInfo(BookCatalog bookCatalog) {

    }

    @Override
    public void saveBookCopy(BookCopy bookCopy) {

    }

    @Override
    public void deleteBookInfo(Isbn isbn) {

    }

    @Override
    public void deleteBookCopy(UUID bookCopyId) {

    }
}
