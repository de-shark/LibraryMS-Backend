package me.deshark.lms.application.cqrs.borrow.query;

import me.deshark.lms.application.info.BorrowRecord;
import me.deshark.lms.common.utils.Page;

/**
 * @author DE_SHARK
 */
public interface BorrowQueryHandler {

    Page<BorrowRecord> handle(GetBorrowRecordsQuery query);
}
