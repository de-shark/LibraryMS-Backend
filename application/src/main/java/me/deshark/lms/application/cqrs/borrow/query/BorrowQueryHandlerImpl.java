package me.deshark.lms.application.cqrs.borrow.query;

import me.deshark.lms.application.info.BorrowRecord;
import me.deshark.lms.application.info.PageResult;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
public class BorrowQueryHandlerImpl implements BorrowQueryHandler {
    @Override
    public PageResult<BorrowRecord> handle(GetBorrowRecordsQuery query) {
        return null;
    }
}
