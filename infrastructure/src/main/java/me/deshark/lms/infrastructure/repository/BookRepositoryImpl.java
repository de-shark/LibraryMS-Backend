package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.entity.BookInfo;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookRepository;
import me.deshark.lms.infrastructure.entity.BookCopyDO;
import me.deshark.lms.infrastructure.entity.BookInfoDO;
import me.deshark.lms.infrastructure.mapper.BookCopyMapper;
import me.deshark.lms.infrastructure.mapper.BookInfoMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/26 19:01
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookRepositoryImpl implements BookRepository {

    private final BookInfoMapper bookInfoMapper;
    private final BookCopyMapper bookCopyMapper;

    @Override
    public BookInfo findByIsbn(Isbn isbn) {
        BookInfoDO bookInfoDO = bookInfoMapper.findByIsbn(isbn.getIsbn());
        if (bookInfoDO == null) {
            throw new BookNotFoundException("未找到ISBN为" + isbn + "的图书");
        }
        return bookInfoMapper.toDomainEntity(bookInfoDO);
    }

    @Override
    public int countAvailableCopies(Isbn isbn) {
        return bookCopyMapper.countAvailableCopiesByIsbn(isbn.getIsbn());
    }

    @Override
    public BookCopy findAvailableBookCopy(Isbn isbn) {
        BookCopyDO bookCopyDO = bookCopyMapper.findAvailableCopyByIsbn(isbn.getIsbn());
        if (bookCopyDO == null) {
            throw new BookCopyNotFoundException("未找到ISBN为" + isbn + "的可用副本");
        }
        return bookCopyMapper.toDomainEntity(bookCopyDO);
    }

    @Override
    public BookCopy findBookCopy(UUID bookCopyId) {
        BookCopyDO bookCopyDO = bookCopyMapper.findById(bookCopyId);
        if (bookCopyDO == null) {
            throw new BookCopyNotFoundException("未找到ID为" + bookCopyId + "的图书副本");
        }
        return bookCopyMapper.toDomainEntity(bookCopyDO);
    }
}
