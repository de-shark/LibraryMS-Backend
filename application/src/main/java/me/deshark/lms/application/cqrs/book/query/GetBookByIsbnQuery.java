package me.deshark.lms.application.cqrs.book.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.core.Query;
import me.deshark.lms.application.info.BookInfo;

/**
 * @author DE_SHARK
 */
@Getter
@RequiredArgsConstructor
public class GetBookByIsbnQuery implements Query<BookInfo> {
    private final String isbn;
}