package me.deshark.lms.domain.model.catalog.vo;

import lombok.Getter;

import java.util.Objects;

/**
 * @author DE_SHARK
 */
@Getter
public class LowInventoryInfo {

    private final Isbn isbn;
    private final int copyCount;

    public LowInventoryInfo(Isbn isbn, int copyCount) {
        this.isbn = isbn;
        this.copyCount = copyCount;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LowInventoryInfo that = (LowInventoryInfo) o;
        return copyCount == that.copyCount && isbn.equals(that.isbn);
    }
    @Override
    public int hashCode() {
        return Objects.hash(isbn, copyCount);
    }
}
