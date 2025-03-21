package me.deshark.lms.domain.repository.borrow;

import me.deshark.lms.domain.model.borrow.aggregate.LoanRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 15:17
 */
public interface BorrowRepository {
    
    // 保存借阅记录
    void save(LoanRecord transaction);
    
    // 根据ID查找借阅记录
    Optional<LoanRecord> findById(UUID transactionId);

    List<LoanRecord> findHistoricalBorrowsByPatron(UUID patronId);

    List<LoanRecord> findCurrentBorrowsByPatron(UUID patronId);
}
