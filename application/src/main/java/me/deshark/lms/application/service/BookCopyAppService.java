package me.deshark.lms.application.service;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.service.catalog.BookCopyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DE_SHARK
 */
@Service
@RequiredArgsConstructor
public class BookCopyAppService {

    private final BookCopyService bookCopyService;

    @Transactional
    public void generateDefaultCopiesForAllBooks() {
        bookCopyService.generateDefaultCopiesForAllBooks();
    }
}
