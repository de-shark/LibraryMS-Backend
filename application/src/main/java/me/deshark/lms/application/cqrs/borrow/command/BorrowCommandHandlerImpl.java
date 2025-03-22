package me.deshark.lms.application.cqrs.borrow.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.domain.model.borrow.aggregate.LoanRecord;
import me.deshark.lms.domain.service.borrow.BorrowService;
import me.deshark.lms.domain.service.borrow.QueryBorrowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowCommandHandlerImpl implements BorrowCommandHandler {

    private final BorrowService borrowService;
    private final QueryBorrowService queryBorrowService;

    @Override
    @Transactional
    public void handle(BorrowCommand command) {
        // 1. 参数校验
        if (command == null || command.getUsername() == null || command.getIsbn() == null) {
            throw new IllegalArgumentException("借阅命令参数不完整");
        }

        // 2. 调用领域服务执行借阅
        borrowService.borrow(command.getUsername(), command.getIsbn());
    }

    @Override
    @Transactional
    public void handle(RenewCommand command) {
        // 1. 参数校验
        if (command == null || command.getRecordId() == null) {
            throw new IllegalArgumentException("续借命令参数不完整");
        }

        // 2. 查找借阅记录
        LoanRecord transaction = queryBorrowService.findBorrowTransactionById(command.getRecordId());
        if (transaction == null) {
            throw new IllegalArgumentException("借阅记录不存在");
        }

        // 3. 调用领域服务执行续借
        borrowService.renew(transaction);
    }

    @Override
    @Transactional
    public void handle(ReturnCommand command) {
        log.info("处理归还请求中: {}", command.getRecordId());
        // 1. 参数校验
        if (command.getRecordId() == null) {
            throw new IllegalArgumentException("归还命令参数不完整");
        }

        // 2. 查找借阅记录
        LoanRecord record = queryBorrowService.findBorrowTransactionById(command.getRecordId());

        // 3. 调用领域服务执行归还
        borrowService.returnBook(record);
    }
}
