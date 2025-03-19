package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommand;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommandHandler;
import me.deshark.lms.application.cqrs.book.command.DeleteBookCommand;
import me.deshark.lms.application.cqrs.book.command.DeleteBookCommandHandler;
import me.deshark.lms.application.cqrs.book.query.GetBookByIsbnQuery;
import me.deshark.lms.application.cqrs.book.query.GetBookByIsbnQueryHandler;
import me.deshark.lms.application.cqrs.book.query.SearchBooksQuery;
import me.deshark.lms.application.cqrs.book.query.SearchBooksQueryHandler;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.application.info.PageResult;
import me.deshark.lms.interfaces.dto.BookResponse;
import me.deshark.lms.interfaces.dto.PageResponse;
import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final CreateBookCommandHandler createBookCommandHandler;
    private final SearchBooksQueryHandler searchBooksQueryHandler;

    @PostMapping
    public ResponseEntity<ResultBody<Void>> createBook(
            @RequestBody CreateBookCommand command) {
        createBookCommandHandler.handle(command);

        // 构建Location header
        URI location = URI.create("/api/books/" + command.isbn());

        return ResponseEntity.created(location)
                .body(ResultBody.<Void>builder().message("入库成功").build());
    }

    private final GetBookByIsbnQueryHandler getBookByIsbnQueryHandler;

    @GetMapping("/{isbn}")
    public ResponseEntity<ResultBody<BookResponse>> getBook(
            @PathVariable("isbn") String isbn) {
        BookInfo bookInfo = getBookByIsbnQueryHandler.handle(new GetBookByIsbnQuery(isbn));
        BookResponse book = BookResponse.builder()
                .isbn(bookInfo.getIsbn())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
                .build();
        return ResponseEntity.ok(ResultBody.<BookResponse>builder().data(book).build());
    }

    @GetMapping
    public ResponseEntity<ResultBody<PageResponse<BookResponse>>> listBooks(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        
        // 执行查询
        PageResult<BookInfo> pageResult = searchBooksQueryHandler.handle(
            new SearchBooksQuery(keyword, page, size)
        );

        // 转换为响应DTO
        List<BookResponse> books = pageResult.data().stream()
            .map(book -> BookResponse.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build())
            .collect(Collectors.toList());

        PageResponse<BookResponse> response = PageResponse.of(
            books,
            pageResult.currentPage(),
            pageResult.totalPages(),
            pageResult.totalItems()
        );

        return ResponseEntity.ok(ResultBody.<PageResponse<BookResponse>>builder()
            .data(response)
            .build());
    }

    private final DeleteBookCommandHandler deleteBookCommandHandler;

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable("isbn") String isbn) {
        deleteBookCommandHandler.handle(new DeleteBookCommand(isbn));
        return ResponseEntity.noContent().build();
    }
}
