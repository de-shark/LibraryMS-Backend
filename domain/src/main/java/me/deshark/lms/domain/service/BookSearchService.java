package me.deshark.lms.domain.service;

import me.deshark.lms.domain.model.catalog.entity.BookInfo;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookRepository;

/**
 * @author DE_SHARK
 * @date 2025/2/16 21:25
 */
public class BookSearchService {

    private final BookRepository bookRepository;

    public BookSearchService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookInfo searchByIsbn(String isbn) {
        Isbn isbnChecked = new Isbn(isbn);
        // TODO 需要将图书副本的数量一起返回
        return bookRepository.findByIsbn(isbnChecked);
    }
}
