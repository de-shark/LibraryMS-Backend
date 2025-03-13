package me.deshark.lms.application.cqrs.borrow.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 获取借阅交易查询
 *
 * <p>用于查询特定借阅记录的详细信息</p>
 *
 * @author DE_SHARK
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBorrowTransactionQuery {
    /**
     * 借阅记录ID
     */
    private UUID transactionId;
}