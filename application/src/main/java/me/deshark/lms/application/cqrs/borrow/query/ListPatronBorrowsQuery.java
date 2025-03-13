package me.deshark.lms.application.cqrs.borrow.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 查询读者借阅记录查询
 *
 * @author DE_SHARK
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPatronBorrowsQuery {
    /**
     * 读者ID
     */
    private UUID patronId;

    /**
     * 借阅状态（可选）
     */
    private String status;

    /**
     * 页码
     */
    private int page;

    /**
     * 每页数量
     */
    private int size;
}