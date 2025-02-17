package me.deshark.lms.domain.model.catalog.entity;

import me.deshark.lms.domain.model.catalog.vo.Isbn;

/**
 * @author DE_SHARK
 * @date 2025/2/15 20:47
 */
// 图书基本信息
public class BookInfo {
    private final Isbn isbn;
    private String title;
    private String author;
    private int countAvailableCopies;


    public BookInfo(Isbn isbn) {
        this.isbn = isbn;
    }

    public BookInfo(Isbn isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCountAvailableCopies() {
        return countAvailableCopies;
    }

    public void setCountAvailableCopies(int countAvailableCopies) {
        this.countAvailableCopies = countAvailableCopies;
    }
}
