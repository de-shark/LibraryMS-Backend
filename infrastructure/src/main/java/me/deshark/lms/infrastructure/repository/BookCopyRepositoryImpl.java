package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.BookCopyStatus;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.catalog.BookCopyRepository;
import me.deshark.lms.infrastructure.entity.BookCopyDO;
import me.deshark.lms.infrastructure.mapper.BookCopyMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<BookCopyDO> bookCopyDOs = copies.stream()
                .map(this::toDataObject)
                .collect(Collectors.toList());
        bookCopyMapper.insertAll(bookCopyDOs);
    }

    private BookCopyDO toDataObject(BookCopy bookCopy) {
        return BookCopyDO.builder()
                .copyId(bookCopy.getCopyId())
                .isbn(bookCopy.getIsbn().toString())
                .status(BookCopyStatus.valueOf(bookCopy.getStatus().name()))
                .loanCount(bookCopy.getLoanCount())
                .build();
    }
}
