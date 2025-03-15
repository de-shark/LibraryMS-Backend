package me.deshark.lms.application.cqrs.borrow.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.info.BorrowTransactionInfo;
import me.deshark.lms.domain.service.BorrowService;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class BorrowCommandHandlerImpl implements BorrowCommandHandler {

    private final BorrowService borrowService;

    @Override
    public BorrowTransactionInfo handle(BorrowCommand command) {
        return null;
    }

    @Override
    public BorrowTransactionInfo handle(RenewCommand command) {
        return null;
    }

    @Override
    public void handle(ReturnCommand command) {

    }
}
