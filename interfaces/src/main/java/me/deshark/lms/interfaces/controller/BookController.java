package me.deshark.lms.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import me.deshark.lms.interfaces.dto.BookResponse;
import me.deshark.lms.application.dto.PageResult;
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
@Tag(name = "BookManager", description = "书籍管理相关API")
public class BookController {

    private final CreateBookCommandHandler createBookCommandHandler;
    private final SearchBooksQueryHandler searchBooksQueryHandler;

    @Operation(summary = "创建图书", description = "添加一本新图书到图书馆目录")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "图书创建成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResultBody.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "请求参数错误",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "图书已存在",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ResultBody<Void>> createBook(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "图书创建请求体",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateBookCommand.class))
            ) CreateBookCommand command) {
        createBookCommandHandler.handle(command);

        // 构建Location header
        URI location = URI.create("/api/books/" + command.isbn());

        return ResponseEntity.created(location)
                .body(ResultBody.<Void>builder().message("入库成功").build());
    }

    private final GetBookByIsbnQueryHandler getBookByIsbnQueryHandler;

    @Operation(summary = "获取图书详情", description = "根据ISBN查询图书详细信息")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "图书信息查询成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResultBody.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "图书不存在",
                    content = @Content
            )
    })
    @GetMapping("/{isbn}")
    public ResponseEntity<ResultBody<BookResponse>> getBook(
            @Parameter(description = "图书ISBN号", example = "9787115549440", required = true)
            @PathVariable("isbn") String isbn) {
        BookInfo bookInfo = getBookByIsbnQueryHandler.handle(new GetBookByIsbnQuery(isbn));
        BookResponse book = BookResponse.builder()
                .isbn(bookInfo.getIsbn())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
                .build();
        return ResponseEntity.ok(ResultBody.<BookResponse>builder().data(book).build());
    }

    @Operation(summary = "获取图书列表", description = "分页查询图书馆所有图书")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "图书列表查询成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResultBody.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<ResultBody<PageResponse<BookResponse>>> listBooks(
            @Parameter(description = "搜索关键词", example = "Java")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "页码", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "每页数量", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
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

    @Operation(summary = "删除图书", description = "根据ISBN删除图书")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "图书删除成功"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "图书不存在",
                    content = @Content
            )
    })
    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "图书ISBN号", example = "9787115549440", required = true)
            @PathVariable("isbn") String isbn) {
        deleteBookCommandHandler.handle(new DeleteBookCommand(isbn));
        return ResponseEntity.noContent().build();
    }
}
