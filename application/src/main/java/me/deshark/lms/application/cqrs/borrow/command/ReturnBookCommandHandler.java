package me.deshark.lms.application.cqrs.borrow.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.info.BorrowTransactionInfo;
import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 归还图书命令处理器
 *
 * <p>处理图书归还请求，协调领域服务完成归还流程</p>
 *
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReturnBookCommandHandler {

    /**
     * 处理归还图书命令
     *
     * @param command 归还图书命令
     * @return 借阅交易信息
     * @throws IllegalArgumentException 当借阅记录不存在时抛出
     */
    @Transactional
    public BorrowTransactionInfo handle(ReturnBookCommand command) {
        log.info("处理归还图书命令: {}", command);

        // 1. 获取借阅记录

        // 2. 执行归还操作

        // 3. 更新读者借阅数量

        // 4. 保存更新后的借阅记录

        // 5. 转换为DTO并返回
        return convertToInfo(null);
    }

    /**
     * 将领域对象转换为DTO
     */
    private BorrowTransactionInfo convertToInfo(BorrowTransaction transaction) {
        // 获取图书信息

        return BorrowTransactionInfo.builder().build();
    }
}