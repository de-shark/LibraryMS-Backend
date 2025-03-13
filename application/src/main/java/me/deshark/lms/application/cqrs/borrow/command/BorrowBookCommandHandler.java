package me.deshark.lms.application.cqrs.borrow.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.info.BorrowTransactionInfo;
import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import me.deshark.lms.domain.service.BorrowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 借阅图书命令处理器
 *
 * <p>处理图书借阅请求，协调领域服务完成借阅流程</p>
 *
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowBookCommandHandler {

    private final BorrowService borrowService;

    /**
     * 处理借阅图书命令
     *
     * @param command 借阅图书命令
     * @return 借阅交易信息
     * @throws IllegalArgumentException 当读者不满足借阅条件或图书不可借时抛出
     */
    @Transactional
    public BorrowTransactionInfo handle(BorrowBookCommand command) {
        log.info("处理借阅图书命令: {}", command);

        // 1. 获取读者信息

        // 2. 执行借阅操作

        // 3. 更新读者借阅数量

        // 4. 转换为DTO并返回
        return convertToInfo(transaction);
    }

    /**
     * 将领域对象转换为DTO
     */
    private BorrowTransactionInfo convertToInfo(BorrowTransaction transaction) {
        // 获取图书信息

        return BorrowTransactionInfo.builder().build();
    }
}