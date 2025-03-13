package me.deshark.lms.application.cqrs.book.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.application.info.PageResult;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.common.Page;
import me.deshark.lms.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class SearchBooksQueryHandler {

    private final BookRepository bookRepository;

    public PageResult<BookInfo> handle(SearchBooksQuery query) {
        // 调用仓储层进行分页查询
        Page<BookMetadata> page = bookRepository.searchBooks(
                query.keyword(),
                query.page() - 1,
                query.size()
        );

        // 转换为BookInfo列表
        List<BookInfo> bookInfos = page.getContent().stream()
            .map(book -> BookInfo.builder()
                .isbn(book.getIsbn().toString())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build())
            .collect(Collectors.toList());

        return PageResult.of(
                bookInfos,
                query.page(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
