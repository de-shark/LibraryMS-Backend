package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommand;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommandHandler;
import me.deshark.lms.application.cqrs.book.query.GetBookByIsbnQuery;
import me.deshark.lms.application.cqrs.book.query.GetBookByIsbnQueryHandler;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.interfaces.dto.ApiResponse;
import me.deshark.lms.interfaces.dto.BookResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final CreateBookCommandHandler createBookCommandHandler;

    @PostMapping
    public ApiResponse<Void> createBook(@RequestBody CreateBookCommand command) {
        createBookCommandHandler.handle(command);
        return ApiResponse.success(null);
    }

    private final GetBookByIsbnQueryHandler getBookByIsbnQueryHandler;

    @GetMapping("/{isbn}")
    public ApiResponse<BookResponse> getBook(@PathVariable String isbn) {
        BookInfo bookInfo = getBookByIsbnQueryHandler.handle(new GetBookByIsbnQuery(isbn));
        BookResponse book = BookResponse.builder()
                .isbn(bookInfo.getIsbn())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
                .build();
        return ApiResponse.success(book);
    }

    @GetMapping
    public ApiResponse<Void> listBooks() {
        // 分页查询图书列表逻辑
        return ApiResponse.error(500, "该功能编写中");
    }

    @PutMapping("/{isbn}")
    public ApiResponse<Void> updateBook(@PathVariable String isbn) {
        // 更新图书信息逻辑
        return ApiResponse.error(500, "该功能编写中");
    }

    @DeleteMapping("/{isbn}")
    public ApiResponse<Void> deleteBook(@PathVariable String isbn) {
        // 删除图书逻辑
        return ApiResponse.error(500, "该功能编写中");
    }
}
