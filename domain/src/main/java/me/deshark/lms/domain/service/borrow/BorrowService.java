package me.deshark.lms.domain.service.borrow;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import me.deshark.lms.domain.model.borrowing.entity.Patron;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;
import me.deshark.lms.domain.repository.borrow.PatronRepository;
import me.deshark.lms.domain.repository.catalog.BookCopyRepository;
import me.deshark.lms.domain.repository.borrow.BorrowRepository;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 15:16
 */
@RequiredArgsConstructor
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookCopyRepository bookCopyRepository;
    private final PatronRepository patronRepository;

    /**
     * 借阅图书
     * @param patronId 借阅者ID
     * @param isbn 图书ISBN
     * @return 借阅记录
     */
    public BorrowTransaction borrow(UUID patronId, String isbn) {

        // 1. 检查用户是否可以借阅
        Patron patron = patronRepository.findById(patronId);
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
