package me.deshark.lms.domain.repository;

import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 15:17
 */
public interface BorrowRepository {
    // 查找可用的图书副本
    BookCopy findAvailableBookCopy(Isbn isbn);
    
    // 保存借阅记录
    void save(BorrowTransaction transaction);
    
    // 更新图书副本状态
    void updateBookCopyStatus(BookCopy bookCopy);
    
    // 根据ID查找借阅记录
    BorrowTransaction findById(UUID transactionId);
    
    // 根据ID查找图书副本
    BookCopy findBookCopyById(UUID bookCopyId);
}
