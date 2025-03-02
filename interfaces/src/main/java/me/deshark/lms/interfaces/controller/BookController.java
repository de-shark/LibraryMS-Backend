package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.book.command.*;
import me.deshark.lms.application.cqrs.book.query.GetBookByIsbnQuery;
import me.deshark.lms.application.cqrs.book.query.GetBookByIsbnQueryHandler;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.interfaces.dto.ApiResponse;
import me.deshark.lms.interfaces.dto.BookResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final CreateBookCommandHandler createBookCommandHandler;

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@RequestBody CreateBookCommand command) {
        createBookCommandHandler.handle(command);
        
        // 获取新创建的图书信息
        BookInfo bookInfo = getBookByIsbnQueryHandler.handle(new GetBookByIsbnQuery(command.isbn()));
        BookResponse book = BookResponse.builder()
                .isbn(bookInfo.getIsbn())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
                .build();
                
        // 构建Location header
        URI location = URI.create("/api/books/" + command.isbn());
        
        return ResponseEntity.created(location)
                .body(ApiResponse.success(book));
    }

    private final GetBookByIsbnQueryHandler getBookByIsbnQueryHandler;

    @GetMapping("/{isbn}")
    public ResponseEntity<ApiResponse<BookResponse>> getBook(@PathVariable("isbn") String isbn) {
        BookInfo bookInfo = getBookByIsbnQueryHandler.handle(new GetBookByIsbnQuery(isbn));
        BookResponse book = BookResponse.builder()
                .isbn(bookInfo.getIsbn())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
                .build();
        return ResponseEntity.ok(ApiResponse.success(book));
    }

    @GetMapping
    public ApiResponse<Void> listBooks() {
        // 分页查询图书列表逻辑
        return ApiResponse.error(500, "该功能编写中");
    }

    private final UpdateBookCommandHandler updateBookCommandHandler;

    @PatchMapping("/{isbn}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody UpdateBookCommand command
    ) {

        // 验证路径参数和请求体中的ISBN是否一致
        if (!isbn.equals(command.isbn())) {
            throw new IllegalArgumentException("Path variable ISBN does not match request body ISBN");
        }

        // 执行更新操作
        updateBookCommandHandler.handle(command);

        // 获取更新后的图书信息
        BookInfo bookInfo = getBookByIsbnQueryHandler.handle(new GetBookByIsbnQuery(isbn));
        BookResponse book = BookResponse.builder()
                .isbn(bookInfo.getIsbn())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
                .build();

        return ResponseEntity.ok(ApiResponse.success(book));
    }

    private final DeleteBookCommandHandler deleteBookCommandHandler;

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn) {
        deleteBookCommandHandler.handle(new DeleteBookCommand(isbn));
        return ResponseEntity.noContent().build();
    }
}
