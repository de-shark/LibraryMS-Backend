package me.deshark.lms.application.cqrs.book.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class UpdateBookCommandHandler {

    private final BookRepository bookRepository;

    public void handle(UpdateBookCommand command) {
        Isbn isbn = new Isbn(command.isbn());
        BookCatalog bookCatalog = bookRepository.findByIsbn(isbn);

        // 更新图书信息

        // 保存更新
        bookRepository.saveBookCatalog(bookCatalog);
    }
}
