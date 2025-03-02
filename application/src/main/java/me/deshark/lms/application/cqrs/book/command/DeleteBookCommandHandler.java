package me.deshark.lms.application.cqrs.book.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookCatalogRepository;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class DeleteBookCommandHandler {

    private final BookCatalogRepository bookCatalogRepository;

    public void handle(DeleteBookCommand command) {
        Isbn isbn = new Isbn(command.isbn());
        bookCatalogRepository.delete(isbn);
    }
}