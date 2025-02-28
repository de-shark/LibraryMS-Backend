package me.deshark.lms.domain.model.catalog.vo;

import java.util.Objects;

/**
 * @author DE_SHARK
 * @date 2025/2/16 14:45
 */
public class Isbn {
    private final String isbn;
    private final String formattedIsbn;

    public Isbn(String isbn) {
        // ISBN 有效性校验
        if (isbn == null || !isbn.matches("^\\d{13}$")) {
            throw new IllegalArgumentException("ISBN 必须是 13 位数字");
        }
        this.isbn = isbn;
        this.formattedIsbn = formatIsbn(isbn);
    }

    private String formatIsbn(String isbn) {
        // 按照 ISBN-13 标准格式化
        return isbn.substring(0, 3) + "-" +
                isbn.charAt(3) + "-" +
                isbn.substring(4, 7) + "-" +
                isbn.substring(7, 12) + "-" +
                isbn.substring(12);
    }

    public String getIsbn() {
        return isbn;
    }

    public String getFormattedIsbn() {
        return formattedIsbn;
    }

    @Override
    public String toString() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Isbn isbn1 = (Isbn) o;
        return Objects.equals(isbn, isbn1.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
