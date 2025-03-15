package me.deshark.lms.application.info;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * 借阅交易信息 INFO
 *
 * <p>用于在应用层和接口层之间传递借阅记录信息</p>
 *
 * @author DE_SHARK
 */
@Data
@Builder
public class BorrowDetail {
    private UUID transactionId;
    private UUID patronId;
    private String isbn;
    private String bookTitle;
    private Date startDate;
    private Date dueDate;
    private Date endDate;
    private String status;
}