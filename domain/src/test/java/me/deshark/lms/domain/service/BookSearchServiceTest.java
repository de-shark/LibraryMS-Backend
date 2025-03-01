package me.deshark.lms.domain.service;

import me.deshark.lms.domain.model.catalog.entity.BookCatalog;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookCatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author DE_SHARK
 * @date 2025/2/17 15:59
 */
class BookSearchServiceTest {

    private BookCatalogRepository bookCatalogRepository;
    private BookSearchService bookSearchService;

    @BeforeEach
    void setUp() {
        // 创建模拟对象
        bookCatalogRepository = Mockito.mock(BookCatalogRepository.class);
        bookSearchService = new BookSearchService(bookCatalogRepository);
    }

    @Test
    void searchByISBN_InvalidISBN_ThrowsException() {
        // 无效的ISBN
        String invalidISBN = "123";
        // 验证是否抛出异常
        assertThrows(IllegalArgumentException.class, () -> bookSearchService.searchByIsbn(invalidISBN));
    }

    @Test
    void searchByISBN_ValidISBN_ReturnsBooks() {
        // 准备测试数据
        String isbnString = "9780000000000";
        Isbn validIsbn = new Isbn(isbnString);
        BookCatalog mockBook = new BookCatalog(validIsbn, "Clean Code", "Robert C. Martin");

        // 配置模拟行为
        when(bookCatalogRepository.findByIsbn(validIsbn)).thenReturn(mockBook);

        // 执行测试
        BookCatalog result = bookSearchService.searchByIsbn(isbnString);

        // 验证结果
        assertNotNull(result);
        assertEquals(validIsbn.toString(), result.getIsbn().toString());
        assertEquals("978-0-000-00000-0", result.getIsbn().getFormattedIsbn());
        assertEquals("Clean Code", result.getTitle());
        assertEquals("Robert C. Martin", result.getAuthor());
//        assertEquals(5, result.getCountAvailableCopies());

        // 验证 repository 方法是否被调用
        verify(bookCatalogRepository).findByIsbn(validIsbn);
    }

    @Test
    void searchByISBN_NoBookFound_ReturnsNull() {
        // 准备测试数据
        String isbnString = "9780000000000";
        Isbn validIsbn = new Isbn(isbnString);

        // 配置模拟行为 - 当找不到图书时返回 null
        when(bookCatalogRepository.findByIsbn(validIsbn)).thenReturn(null);

        // 执行测试
        BookCatalog result = bookSearchService.searchByIsbn(isbnString);

        // 验证结果
        assertNull(result, "当找不到图书时应该返回 null");

        // 验证 repository 方法是否被调用
        verify(bookCatalogRepository).findByIsbn(validIsbn);
    }
}

