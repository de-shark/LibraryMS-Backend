package me.deshark.lms.application.cqrs.borrow.command;

import me.deshark.lms.application.info.BorrowTransactionInfo;

/**
 * @author DE_SHARK
 */
public interface BorrowCommandHandler {
    BorrowTransactionInfo handle(BorrowCommand command);

    BorrowTransactionInfo handle(RenewCommand command);
    void handle(ReturnCommand command);
}
