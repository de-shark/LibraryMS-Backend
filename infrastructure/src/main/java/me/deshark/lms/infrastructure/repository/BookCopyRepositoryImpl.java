package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.common.exception.book.BookCatalogException;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.catalog.BookCopyRepository;
import me.deshark.lms.infrastructure.entity.BookCopyDO;
import me.deshark.lms.infrastructure.entity.BookInventoryViewDO;
import me.deshark.lms.infrastructure.enums.CopyStatusType;
import me.deshark.lms.infrastructure.mapper.BookCopyMapper;
import me.deshark.lms.infrastructure.mapper.BookInventoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author DE_SHARK
 * @date 2025/2/26 19:01
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class BookCopyRepositoryImpl implements BookCopyRepository {

    private final BookCopyMapper bookCopyMapper;
    private final BookInventoryMapper bookInventoryMapper;

    @Override
    public int countAvailableCopies(Isbn isbn) {
        BookInventoryViewDO bookInventoryViewDO = bookInventoryMapper.findByIsbn(isbn.toString())
                .orElseThrow(() -> new BookCatalogException("没有找到库存信息"));
        return bookInventoryViewDO.getAvailableCount();
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
        log.info("正在更新图书副本状态: {}", bookCopy.getStatus());
        bookCopyMapper.updateStatus(bookCopy.getCopyId(), CopyStatusType.valueOf(bookCopy.getStatus().name()));
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
                .status(CopyStatusType.valueOf(bookCopy.getStatus().name()))
                .build();
    }
}
