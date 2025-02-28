package me.deshark.lms.application.cqrs.book.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.repository.BookQueryRepository;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class SearchBooksQueryHandler {

    private final BookQueryRepository bookQueryRepository;
}
