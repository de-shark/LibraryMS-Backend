package me.deshark.lms.application.cqrs.book.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookCatalogRepository;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class UpdateBookCommandHandler {

    private final BookCatalogRepository bookCatalogRepository;

    public void handle(UpdateBookCommand command) {
        Isbn isbn = new Isbn(command.isbn());

        BookCatalog bookCatalog = bookCatalogRepository.findByIsbn(isbn);

        // 执行领域对象更新
        bookCatalog.updateDetails(
                command.title(),
                command.author(),
                command.publisher(),
                command.publishYear(),
                command.description()
        );

        // 保存更新
        bookCatalogRepository.update(bookCatalog);
    }
}
