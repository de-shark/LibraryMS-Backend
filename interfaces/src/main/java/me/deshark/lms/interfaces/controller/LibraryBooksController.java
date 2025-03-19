package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.service.BookCopyAppService;
import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/librarian/books")
@RequiredArgsConstructor
public class LibraryBooksController {

    private final BookCopyAppService bookCopyAppService;

    @PostMapping("/generate-copies")
    public ResponseEntity<ResultBody<Void>> generateBookCopies() {
        bookCopyAppService.generateDefaultCopiesForAllBooks();
        return new ResponseEntity<>(ResultBody.success("创建成功"), HttpStatus.CREATED);
    }
}
