package me.deshark.lms.application.info;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * 借阅交易信息DTO
 *
 * <p>用于在应用层和接口层之间传递借阅记录信息</p>
 *
 * @author DE_SHARK
 */
@Data
@Builder
public class BorrowTransactionInfo {
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