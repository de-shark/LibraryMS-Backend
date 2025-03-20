package me.deshark.lms.domain.repository.borrow;

import me.deshark.lms.common.utils.Page;
import me.deshark.lms.domain.model.borrow.query.BorrowRecordQuery;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
public interface BorrowQueryRepository {

    Page<BorrowRecordQuery> getPatronTransactions(UUID userId, int pageNumber, int pageSize);
}
