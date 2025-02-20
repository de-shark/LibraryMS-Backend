package me.deshark.lms.domain.service;

import com.github.f4b6a3.uuid.alt.GUID;
import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import me.deshark.lms.domain.model.borrowing.entity.Patron;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookRepository;
import me.deshark.lms.domain.repository.BorrowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author DE_SHARK
 * @date 2025/2/19 19:32
 */
class BorrowServiceTest {

    private BorrowRepository borrowRepository;
    private BookRepository bookRepository;
    private BorrowService borrowService;

    private Patron validPatron;
    private Patron invalidPatron;
    private final String validIsbn = "9780000000000";
    private final Isbn isbn = new Isbn(validIsbn);
    private final UUID bookCopyId = GUID.v7().toUUID();

    @BeforeEach
    void setUp() {
        borrowRepository = Mockito.mock(BorrowRepository.class);
        bookRepository = Mockito.mock(BookRepository.class);
        borrowService = new BorrowService(borrowRepository, bookRepository);

        // 初始化有效/无效用户
        validPatron = new Patron(GUID.v7().toUUID(), 0, 90);
        invalidPatron = new Patron(GUID.v7().toUUID(), 0, 50);
    }

    @Test
    void borrowBook_SuccessfulBorrow_ReturnTransaction() {
        // 准备测试数据
        BookCopy mockBookCopy = new BookCopy(bookCopyId);
        mockBookCopy.setIsbn(new Isbn(validIsbn));
        mockBookCopy.setStatus("AVAILABLE");

        // 配置模拟行为
        when(bookRepository.countAvailableCopies(isbn)).thenReturn(1);
        when(bookRepository.findAvailableBookCopy(isbn)).thenReturn(mockBookCopy);

        // 执行测试
        BorrowTransaction result = borrowService.borrow(validPatron, validIsbn);

        // 验证结果
        assertNotNull(result);
        assertEquals(bookCopyId, result.getBookCopyId());
        assertEquals("BORROWED", result.getStatus());
        assertTrue(result.getStartDate().before(new Date()));
        assertTrue(result.getDueDate().after(new Date()));
    }
}
