package me.deshark.lms.application.cqrs.borrow.command;

/**
 * @author DE_SHARK
 */
public interface BorrowCommandHandler {

    void handle(BorrowCommand command);

    void handle(RenewCommand command);

    void handle(ReturnCommand command);
}
