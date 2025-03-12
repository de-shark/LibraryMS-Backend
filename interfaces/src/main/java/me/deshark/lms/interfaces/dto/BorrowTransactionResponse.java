package me.deshark.lms.interfaces.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * 借阅交易响应DTO
 *
 * <p>用于向客户端返回借阅记录信息</p>
 *
 * @author deshark
 */
@Data
@Builder
public class BorrowTransactionResponse {
    /**
     * 借阅记录ID
     */
    private UUID transactionId;

    /**
     * 图书副本ID
     */
    private UUID bookCopyId;

    /**
     * 读者ID
     */
    private UUID patronId;

    /**
     * 图书ISBN
     */
    private String isbn;

    /**
     * 图书标题
     */
    private String bookTitle;

    /**
     * 借阅开始日期
     */
    private Date startDate;

    /**
     * 应还日期
     */
    private Date dueDate;

    /**
     * 实际归还日期
     */
    private Date endDate;

    /**
     * 借阅状态
     */
    private String status;
}