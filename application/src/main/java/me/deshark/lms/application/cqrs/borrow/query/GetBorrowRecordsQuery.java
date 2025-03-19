package me.deshark.lms.application.cqrs.borrow.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
public class GetBorrowRecordsQuery {
    /**
     * 读者用户名
     */
    private String username;

    /**
     * 页面
     */
    private int page;

    /**
     * 页大小
     */
    private int size;
}