package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.common.exception.book.BookNotFoundException;
import me.deshark.lms.common.utils.Page;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.model.catalog.vo.LowInventoryInfo;
import me.deshark.lms.domain.repository.catalog.BookRepository;
import me.deshark.lms.infrastructure.BookConverter;
import me.deshark.lms.infrastructure.entity.BookDO;
import me.deshark.lms.infrastructure.entity.BookInventoryViewDO;
import me.deshark.lms.infrastructure.mapper.BookInventoryMapper;
import me.deshark.lms.infrastructure.mapper.BookMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final BookMapper bookMapper;
    private final BookInventoryMapper bookInventoryMapper;

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookMapper.existsByIsbn(isbn);
    }

    @Override
    public Optional<BookMetadata> findByIsbn(Isbn isbn) {
        Optional<BookDO> bookDO = bookMapper.findByIsbn(isbn.toString());
        if (bookDO.isEmpty()) {
            throw new BookNotFoundException("未找到ISBN为" + isbn + "的图书");
        }
        return bookDO.map(book -> {
            // 查询库存信息
            int availableCopies = bookInventoryMapper.findByIsbn(book.getIsbn())
                    .map(BookInventoryViewDO::getCurrentCopyCount)
                    .orElse(0);
            // 转换为 BookMetadata 并设置所有字段
            BookMetadata metadata = BookConverter.INSTANCE.doToEntity(book);
            metadata.setAvailableCopies(availableCopies);
            return metadata;
        });
    }

    @Override
    public void save(BookMetadata bookMetadata) {
        BookDO bookDO = BookDO.builder()
                .isbn(bookMetadata.getIsbn().toString())
                .title(bookMetadata.getTitle())
                .author(bookMetadata.getAuthor())
                .publisher(bookMetadata.getPublisher())
                .publishedYear(bookMetadata.getPublishedYear())
                .build();
        bookMapper.insert(bookDO);
    }

    @Override
    public void delete(Isbn isbn) {
        bookMapper.delete(isbn.toString());
    }

    @Override
    public Page<BookMetadata> searchBooks(String keyword, int pageNumber, int pageSize) {
        // 根据关键词查询
        List<BookDO> bookDOs;
        long total;

        if (keyword == null || keyword.isBlank()) {
            // 无关键词时查询全部
            bookDOs = bookMapper.findAll();
            total = bookMapper.count();
        } else {
            // 有关键词时按标题和作者搜索
            bookDOs = bookMapper.findByTitleContaining(keyword);
            total = bookDOs.size();
        }

        // 分页处理
        int start = pageNumber * pageSize;
        int end = Math.min((start + pageSize), bookDOs.size());
        List<BookDO> pagedDOs = bookDOs.subList(start, end);

        // 转换为领域模型
        List<BookMetadata> bookMetadatas = pagedDOs.stream()
                .map(bookDO -> {
                    BookMetadata metadata = BookConverter.INSTANCE.doToEntity(bookDO);
                    // 查询库存信息
                    int availableCopies = bookInventoryMapper.findByIsbn(bookDO.getIsbn())
                            .map(BookInventoryViewDO::getCurrentCopyCount)
                            .orElse(0);
                    metadata.setAvailableCopies(availableCopies);
                    return metadata;
                })
                .collect(Collectors.toList());
        Page<BookMetadata> result = new Page<>(pageNumber, pageSize);
        result.setRecords(bookMetadatas);
        result.setTotal(total);
        return result;
    }

    @Override
    public List<LowInventoryInfo> findBooksWithLowInventory(int minCopyCount) {
        List<BookInventoryViewDO> bookInventoryViewDOS = bookMapper.findBooksWithLowInventory(minCopyCount);
        List<LowInventoryInfo> result = new ArrayList<>();

        for (BookInventoryViewDO info : bookInventoryViewDOS) {
            Isbn isbn = new Isbn(info.getIsbn());
            LowInventoryInfo lowInventoryInfo = new LowInventoryInfo(isbn, info.getCurrentCopyCount());
            result.add(lowInventoryInfo);
        }

        return result;
    }

    @Override
    public void updateCoverImage(String isbn, String coverImage) {
        bookMapper.updateCoverImage(isbn, coverImage);
    }

    @Override
    public Optional<String> findCoverImageByIsbn(String isbn) {
        return bookMapper.findByIsbn(isbn)
                .map(BookDO::getCoverImage);
    }
}
