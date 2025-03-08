package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommand;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommandHandler;
import me.deshark.lms.application.cqrs.book.command.DeleteBookCommand;
import me.deshark.lms.application.cqrs.book.command.DeleteBookCommandHandler;
import me.deshark.lms.application.cqrs.book.query.GetBookByIsbnQuery;
import me.deshark.lms.application.cqrs.book.query.GetBookByIsbnQueryHandler;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.interfaces.dto.BookResponse;
import me.deshark.lms.interfaces.dto.ResultBody;
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
    public ResponseEntity<ResultBody<BookResponse>> createBook(@RequestBody CreateBookCommand command) {
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
                .body(ResultBody.<BookResponse>builder()
                        .data(book)
                        .build());
    }

    private final GetBookByIsbnQueryHandler getBookByIsbnQueryHandler;

    @GetMapping("/{isbn}")
    public ResponseEntity<ResultBody<BookResponse>> getBook(@PathVariable("isbn") String isbn) {
        BookInfo bookInfo = getBookByIsbnQueryHandler.handle(new GetBookByIsbnQuery(isbn));
        BookResponse book = BookResponse.builder()
                .isbn(bookInfo.getIsbn())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
                .build();
        return ResponseEntity.ok(ResultBody.<BookResponse>builder().data(book).build());
    }

    @GetMapping
    public ResultBody<Void> listBooks() {
        // 分页查询图书列表逻辑
        return ResultBody.<Void>builder().message("该功能编写中").build();
    }

    private final DeleteBookCommandHandler deleteBookCommandHandler;

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn) {
        deleteBookCommandHandler.handle(new DeleteBookCommand(isbn));
        return ResponseEntity.noContent().build();
    }
}
