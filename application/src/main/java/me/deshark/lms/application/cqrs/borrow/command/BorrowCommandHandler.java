package me.deshark.lms.application.cqrs.borrow.command;

import me.deshark.lms.application.info.BorrowTransactionInfo;

/**
 * @author DE_SHARK
 */
public interface BorrowCommandHandler {
    BorrowTransactionInfo handle(BorrowBookCommand command);

    BorrowTransactionInfo handle(RenewBorrowCommand command);
    void handle(ReturnBookCommand command);
}
