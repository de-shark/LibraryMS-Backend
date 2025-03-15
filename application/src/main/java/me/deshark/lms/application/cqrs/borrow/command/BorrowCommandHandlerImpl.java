package me.deshark.lms.application.cqrs.borrow.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.service.borrow.BorrowService;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class BorrowCommandHandlerImpl implements BorrowCommandHandler {

    private final BorrowService borrowService;

    @Override
    public void handle(BorrowCommand command) {

    }

    @Override
    public void handle(RenewCommand command) {

    }

    @Override
    public void handle(ReturnCommand command) {

    }
}
