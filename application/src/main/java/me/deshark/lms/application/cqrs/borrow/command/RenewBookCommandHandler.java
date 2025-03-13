package me.deshark.lms.application.cqrs.borrow.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.info.BorrowTransactionInfo;
import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 续借图书命令处理器
 *
 * <p>处理图书续借请求，协调领域服务完成续借流程</p>
 *
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RenewBookCommandHandler {

    /**
     * 处理续借图书命令
     *
     * @param command 续借图书命令
     * @return 借阅交易信息
     * @throws IllegalArgumentException 当借阅记录不存在或不满足续借条件时抛出
     */
    @Transactional
    public BorrowTransactionInfo handle(RenewBookCommand command) {
        log.info("处理续借图书命令: {}", command);

        // 1. 获取借阅记录

        // 2. 执行续借操作

        // 3. 保存更新后的借阅记录

        // 4. 转换为DTO并返回
        return null;
    }

    /**
     * 将领域对象转换为DTO
     */
    private BorrowTransactionInfo convertToInfo(BorrowTransaction transaction) {
        // 获取图书信息

        return BorrowTransactionInfo.builder().build();
    }
}