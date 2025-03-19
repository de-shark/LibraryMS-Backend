package me.deshark.lms.application.cqrs.borrow.query;

import me.deshark.lms.application.info.BorrowRecord;
import me.deshark.lms.application.info.PageResult;

/**
 * @author DE_SHARK
 */
public interface BorrowQueryHandler {

    PageResult<BorrowRecord> handle(GetBorrowRecordsQuery query);
}
