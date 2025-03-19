package me.deshark.lms.domain.model.catalog;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DE_SHARK
 */
@RequiredArgsConstructor
public class Book {

    private final Isbn isbn;
    private BookMetadata metadata;
    private List<BookCopy> copies = new ArrayList<>();

    /**
     * 领域方法：添加新副本
     */
    public void addCopy() {
        copies.add(BookCopy.copyOf(isbn));
    }

    public int availableCopiesCount() {
        return (int) copies.stream()
                .filter(BookCopy::isAvailable)
                .count();
    }
}
