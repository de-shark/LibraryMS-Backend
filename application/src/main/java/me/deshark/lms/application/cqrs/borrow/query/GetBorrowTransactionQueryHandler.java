package me.deshark.lms.application.cqrs.borrow.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.info.BorrowTransactionInfo;
import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import org.springframework.stereotype.Service;

/**
 * 获取借阅记录查询处理器
 *
 * <p>处理获取单个借阅记录的查询请求</p>
 *
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetBorrowTransactionQueryHandler {

    /**
     * 处理获取借阅记录查询
     *
     * @param query 获取借阅记录查询
     * @return 借阅交易信息
     * @throws IllegalArgumentException 当借阅记录不存在时抛出
     */
    public BorrowTransactionInfo handle(GetBorrowTransactionQuery query) {
        log.info("处理获取借阅记录查询: {}", query);

        // 获取借阅记录

        // 转换为DTO并返回
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