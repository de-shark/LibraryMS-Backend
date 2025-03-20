package me.deshark.lms.domain.model.catalog.agregate;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.catalog.entity.BookCopy;
import me.deshark.lms.domain.model.catalog.entity.BookMetadata;
import me.deshark.lms.domain.model.catalog.vo.Isbn;

/**
 * @author DE_SHARK
 */
@RequiredArgsConstructor
public class Book {

    private final Isbn isbn;
    private BookMetadata metadata;
    private BookCopy copy;
}
