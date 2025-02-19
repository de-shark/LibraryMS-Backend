package me.deshark.lms.domain.model.catalog.entity;

import me.deshark.lms.domain.model.catalog.vo.Isbn;

import java.util.Date;
import java.util.UUID;

/**
 * @author DE_SHARK
 * @date 2025/2/16 14:44
 */
// 图书副本
public class BookCopy {
    private final UUID bookCopyId;
    private Isbn isbn;
    private Date acquisitionDate;
    private String status;

    public BookCopy(UUID bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public UUID getBookCopyId() {
        return bookCopyId;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public void setIsbn(Isbn isbn) {
        this.isbn = isbn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
