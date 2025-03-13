package me.deshark.lms.application.cqrs.borrow.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.info.BorrowTransactionInfo;
import me.deshark.lms.application.info.PageResult;
import me.deshark.lms.domain.model.borrowing.aggregate.BorrowTransaction;
import org.springframework.stereotype.Service;


/**
 * 查询读者借阅记录查询处理器
 *
 * <p>处理分页查询读者借阅记录的请求</p>
 *
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ListPatronBorrowsQueryHandler {

    /**
     * 处理查询读者借阅记录查询
     *
     * @param query 查询读者借阅记录查询
     * @return 分页借阅交易信息
     * @throws IllegalArgumentException 当读者不存在时抛出
     */
    public PageResult<BorrowTransactionInfo> handle(ListPatronBorrowsQuery query) {
        log.info("处理查询读者借阅记录查询: {}", query);

        // 验证读者是否存在

        // 查询借阅记录

        // 转换为DTO

        // 构建分页结果
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