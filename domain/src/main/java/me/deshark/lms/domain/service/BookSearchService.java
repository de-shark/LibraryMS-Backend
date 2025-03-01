package me.deshark.lms.domain.service;

import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookCatalogRepository;

/**
 * @author DE_SHARK
 * @date 2025/2/16 21:25
 */
public class BookSearchService {

    private final BookCatalogRepository bookCatalogRepository;

    public BookSearchService(BookCatalogRepository bookCatalogRepository) {
        this.bookCatalogRepository = bookCatalogRepository;
    }

    public BookCatalog searchByIsbn(String isbn) {
        Isbn isbnChecked = new Isbn(isbn);
        // TODO 需要将图书副本的数量一起返回
        return bookCatalogRepository.findByIsbn(isbnChecked);
    }
}
