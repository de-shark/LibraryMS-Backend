package me.deshark.lms.domain.repository.borrow;

import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;

import java.util.List;
import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 15:17
 */
public interface BorrowRepository {
    
    // 保存借阅记录
    void save(BorrowTransaction transaction);
    
    // 根据ID查找借阅记录
    BorrowTransaction findById(UUID transactionId);

    List<BorrowTransaction> findHistoricalBorrowsByPatron(UUID patronId);

    List<BorrowTransaction> findCurrentBorrowsByPatron(UUID patronId);
}
