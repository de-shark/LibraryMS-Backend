package me.deshark.lms.domain.repository.borrow;

import me.deshark.lms.domain.model.borrow.aggregate.LoanRecord;
import me.deshark.lms.domain.model.common.Page;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
public interface BorrowQueryRepository {

    Page<LoanRecord> getPatronTransactions(UUID userId, int pageNumber, int pageSize);
}
