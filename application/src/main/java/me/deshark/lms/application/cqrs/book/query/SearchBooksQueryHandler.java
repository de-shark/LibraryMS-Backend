package me.deshark.lms.application.cqrs.book.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.common.utils.Page;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.repository.catalog.BookRepository;
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

    public Page<BookInfo> handle(SearchBooksQuery query) {
        // 调用仓储层进行分页查询
        me.deshark.lms.domain.model.common.Page<BookMetadata> page = bookRepository.searchBooks(
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

        Page<BookInfo> result = new Page<>(query.page(), query.size());
        result.setRecords(bookInfos);
        result.setTotal(page.getTotalElements());
        return result;
    }
}
