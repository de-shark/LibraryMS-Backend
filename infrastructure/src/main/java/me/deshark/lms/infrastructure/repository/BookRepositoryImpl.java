package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.common.exception.book.BookNotFoundException;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.model.common.Page;
import me.deshark.lms.domain.repository.BookRepository;
import me.deshark.lms.infrastructure.entity.BookDO;
import me.deshark.lms.infrastructure.mapper.BookMapper;
import org.springframework.stereotype.Repository;

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

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookMapper.existsByIsbn(isbn);
    }

    @Override
    public BookMetadata findByIsbn(Isbn isbn) {
        Optional<BookDO> bookCatalogDO = bookMapper.findByIsbn(isbn.toString());
        if (bookCatalogDO.isEmpty()) {
            throw new BookNotFoundException("未找到ISBN为" + isbn + "的图书");
        }
        return BookMetadata.builder()
                .isbn(isbn)
                .title(bookCatalogDO.get().getTitle())
                .author(bookCatalogDO.get().getAuthor())
                .build();
    }

    @Override
    public void save(BookMetadata bookMetadata) {
        BookDO bookDO = BookDO.builder()
                .isbn(bookMetadata.getIsbn().toString())
                .title(bookMetadata.getTitle())
                .author(bookMetadata.getAuthor())
                .publisher(bookMetadata.getPublisher())
                .publishedDate(bookMetadata.getPublishedDate())
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
                .map(bookDO -> BookMetadata.builder()
                        .isbn(new Isbn(bookDO.getIsbn()))
                        .title(bookDO.getTitle())
                        .author(bookDO.getAuthor())
                        .publisher(bookDO.getPublisher())
                        .publishedDate(bookDO.getPublishedDate())
                        .build())
                .collect(Collectors.toList());

        return new Page<>(bookMetadatas, pageNumber, pageSize, total);
    }
}
