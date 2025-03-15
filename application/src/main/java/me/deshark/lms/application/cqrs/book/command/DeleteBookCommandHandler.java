package me.deshark.lms.application.cqrs.book.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.catalog.BookRepository;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class DeleteBookCommandHandler {

    private final BookRepository bookRepository;

    public void handle(DeleteBookCommand command) {
        Isbn isbn = new Isbn(command.isbn());
        bookRepository.delete(isbn);
    }
}