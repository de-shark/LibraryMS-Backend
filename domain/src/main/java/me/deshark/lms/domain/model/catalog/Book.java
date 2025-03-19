package me.deshark.lms.domain.model.catalog;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author DE_SHARK
 */
@RequiredArgsConstructor
public class Book {

    private final Isbn isbn;
    private BookMetadata metadata;
    private List<BookCopy> copies = new ArrayList<>();

    public void checkCopyDemand(int expectedCount) {
        if (expectedCount <= copies.size()) {
            throw new IllegalStateException("当前副本数已满足要求");
        }
    }

    /**
     * 领域方法：生成指定数量的新副本
     */
    public List<BookCopy> generateNewCopies(int requiredCount) {
        return IntStream.range(0, requiredCount)
                .mapToObj(i -> BookCopy.copyOf(isbn))
                .toList();
    }

    public int availableCopiesCount() {
        return (int) copies.stream()
                .filter(BookCopy::isAvailable)
                .count();
    }
}
