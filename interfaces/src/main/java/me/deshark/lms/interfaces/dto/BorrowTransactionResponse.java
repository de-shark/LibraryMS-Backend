package me.deshark.lms.interfaces.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * 借阅交易响应DTO
 * 
 * <p>用于向客户端返回借阅记录的详细信息</p>
 */
@Data
@Builder
public class BorrowTransactionResponse {
    private UUID transactionId;
    private UUID bookCopyId;
    private UUID patronId;
    private String isbn;
    private String bookTitle;
    private Date startDate;
    private Date dueDate;
    private Date endDate;
    private String status;
}