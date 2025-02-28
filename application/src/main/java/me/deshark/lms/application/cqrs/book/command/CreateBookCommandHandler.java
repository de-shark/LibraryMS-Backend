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
public class CreateBookCommandHandler {

    private final BookRepository bookRepository;

    public void handle(CreateBookCommand command) {
        // 验证ISBN
        Isbn isbn = new Isbn(command.isbn());

        // 创建图书信息
        BookCatalog bookCatalog = new BookCatalog(isbn, command.title(), command.author());

        // 保存到仓库
        bookRepository.saveBookCatalog(bookCatalog);
    }
}