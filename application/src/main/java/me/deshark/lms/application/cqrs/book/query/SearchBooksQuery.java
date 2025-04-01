package me.deshark.lms.application.cqrs.book.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.deshark.lms.application.cqrs.core.Query;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.common.utils.Page;

/**
 * @author DE_SHARK
 */

@Getter
@AllArgsConstructor
public class SearchBooksQuery implements Query<Page<BookInfo>> {
    private String keyword;
    private int page;
    private int size;
}
