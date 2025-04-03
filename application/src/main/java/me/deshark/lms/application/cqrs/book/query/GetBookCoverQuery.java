package me.deshark.lms.application.cqrs.book.query;

import lombok.Data;
import me.deshark.lms.application.cqrs.core.Query;

@Data
public class GetBookCoverQuery implements Query<String> {
    private final String isbn;
}
