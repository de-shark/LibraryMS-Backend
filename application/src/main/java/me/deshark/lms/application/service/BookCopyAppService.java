package me.deshark.lms.application.service;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.service.catalog.BookCopyService;
import org.springframework.stereotype.Service;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class BookCopyAppService {

    private final BookCopyService bookCopyService;

    public void generateDefaultCopiesForAllBooks() {
        bookCopyService.generateDefaultCopiesForAllBooks();
    }
}
