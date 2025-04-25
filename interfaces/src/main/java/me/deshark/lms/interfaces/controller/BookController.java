package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommand;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommandHandler;
import me.deshark.lms.application.cqrs.book.command.DeleteBookCommand;
import me.deshark.lms.application.cqrs.book.command.DeleteBookCommandHandler;
import me.deshark.lms.application.cqrs.book.command.UploadBookCoverCommand;
import me.deshark.lms.application.cqrs.book.command.UploadBookCoverCommandHandler;
import me.deshark.lms.application.cqrs.book.query.*;
import me.deshark.lms.application.info.BookInfo;
import me.deshark.lms.common.exception.book.BookNotFoundException;
import me.deshark.lms.common.utils.Page;
import me.deshark.lms.interfaces.converter.BookDetailConverter;
import me.deshark.lms.interfaces.dto.BookResponse;
import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

/**
 * @author DE_SHARK
 */
@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final CreateBookCommandHandler createBookCommandHandler;
    private final SearchBooksQueryHandler searchBooksQueryHandler;
    private final GetBookByIsbnQueryHandler getBookByIsbnQueryHandler;
    private final DeleteBookCommandHandler deleteBookCommandHandler;
    private final UploadBookCoverCommandHandler uploadBookCoverCommandHandler;
    private final GetBookCoverQueryHandler getBookCoverQueryHandler;

    @PostMapping
    public ResponseEntity<ResultBody<Void>> createBook(
            @RequestBody CreateBookCommand command) {
        createBookCommandHandler.handle(command);

        // 构建Location header
        URI location = URI.create("/api/books/" + command.getIsbn());

        return ResponseEntity.created(location)
                .body(ResultBody.<Void>builder().message("入库成功").build());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<ResultBody<BookResponse>> getBook(
            @PathVariable("isbn") String isbn) {

        BookResponse book = getBookByIsbnQueryHandler.handle(new GetBookByIsbnQuery(isbn))
                .map(BookDetailConverter.INSTANCE::infoToResponse)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        return ResponseEntity.ok(ResultBody.<BookResponse>builder().data(book).build());
    }

    @GetMapping
    public ResponseEntity<ResultBody<Page<BookInfo>>> listBooks(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {

        log.info("开始处理请求：分页查询图书");

        // 执行查询
        Page<BookInfo> pageData = searchBooksQueryHandler.handle(
            new SearchBooksQuery(keyword, page, size)
        ).orElseThrow(NullPointerException::new);

        log.info("请求处理完成：分页查询图书");

        return ResponseEntity.ok(ResultBody.<Page<BookInfo>>builder()
                .data(pageData)
            .build());
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable("isbn") String isbn) {
        deleteBookCommandHandler.handle(new DeleteBookCommand(isbn));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{isbn}/cover")
    public ResponseEntity<ResultBody<Void>> uploadBookCover(
            @PathVariable("isbn") String isbn,
            @RequestParam("file") MultipartFile file) {

        log.info("执行请求：上传图书封面");

        uploadBookCoverCommandHandler.handle(
            new UploadBookCoverCommand(isbn, file)
        );
        
        return ResponseEntity.ok().body(ResultBody.success("封面图片上传成功"));
    }

    @GetMapping("/{isbn}/cover")
    public ResponseEntity<ResultBody<String >> downloadBookCover(
            @PathVariable("isbn") String isbn) {

        log.info("执行请求：获取图书封面url");

        String url = getBookCoverQueryHandler.handle(new GetBookCoverQuery(isbn))
                .orElseThrow(() -> new BookNotFoundException("封面不存在"));

        return ResponseEntity.ok().body(ResultBody.success(url, "封面获取成功"));
    }
}
