package me.deshark.lms.domain.service;

import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import me.deshark.lms.domain.model.borrowing.entity.Patron;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.BookCopyRepository;
import me.deshark.lms.domain.repository.BorrowRepository;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author DE_SHARK
 * @date 2025/2/16 15:16
 */
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookCopyRepository bookCopyRepository;

    public BorrowService(
            BorrowRepository borrowRepository,
            BookCopyRepository bookCopyRepository
    ) {
        this.borrowRepository = borrowRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    /**
     * 借阅图书
     * @param patron 借阅者
     * @param isbn 图书ISBN
     * @return 借阅记录
     */
    public BorrowTransaction borrow(Patron patron, String isbn) {

        // 1. 检查用户是否可以借阅
        patron.canBorrow();

        // 2. 检查图书是否可借
        Isbn vaildIsbn = new Isbn(isbn);
        if (bookCopyRepository.countAvailableCopies(vaildIsbn) < 1) {
            throw new IllegalArgumentException("Book is not available");
        }

        // 3. 获取可用的图书副本
        BookCopy bookCopy = bookCopyRepository.findAvailableBookCopy(vaildIsbn);

        // 4. 创建借阅记录
        BorrowTransaction borrowTransaction = new BorrowTransaction(bookCopy.getBookCopyId(), patron);

        // 5. 保存借阅记录
        borrowRepository.save(borrowTransaction);

        // 6. 更新图书副本状态
        bookCopy.setStatus("BORROWED");
        bookCopyRepository.updateBookCopyStatus(bookCopy);

        return borrowTransaction;
    }

    /**
     * 续借图书
     * @return 更新后的借阅记录
     */
    public BorrowTransaction renew(BorrowTransaction transaction) {
        transaction.renew();
        borrowRepository.save(transaction);
        return transaction;
    }

    /**
     * 归还图书
     * @return 更新后的借阅记录
     */
    public BorrowTransaction returnBook(BorrowTransaction transaction) {
        // 1. 更新借阅记录状态
        transaction.returnBook();
        // 2. 更新图书副本状态
        BookCopy bookCopy = bookCopyRepository.findBookCopy(transaction.getBookCopyId());
        bookCopy.setStatus("ACTIVATE");

        return transaction;
    }
}
