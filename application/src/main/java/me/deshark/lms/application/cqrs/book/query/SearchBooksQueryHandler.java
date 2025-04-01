package me.deshark.lms.application.cqrs.book.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.converter.BookMetadataConverter;
import me.deshark.lms.application.cqrs.core.QueryHandler;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.common.utils.Page;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.repository.catalog.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class SearchBooksQueryHandler
        implements QueryHandler<SearchBooksQuery, Page<BookInfo>> {

    private final BookRepository bookRepository;

    @Override
    public Optional<Page<BookInfo>> handle(SearchBooksQuery query) {
        // 调用仓储层进行分页查询
        Page<BookMetadata> page = bookRepository.searchBooks(
                query.getKeyword(),
                query.getPage() - 1,
                query.getSize()
        );

        // 转换为BookInfo列表
        List<BookInfo> bookInfos = page.getRecords().stream()
            .map(BookMetadataConverter.INSTANCE::entityToInfo)
            .collect(Collectors.toList());

        Page<BookInfo> result = new Page<>(query.getPage(), query.getSize());
        result.setRecords(bookInfos);
        result.setTotal(page.getTotal());
        return Optional.of(result);
    }
}
