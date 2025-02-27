package me.deshark.lms.interfaces.controller;

import me.deshark.lms.interfaces.dto.*;
import org.springframework.web.bind.annotation.*;

/**
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    public ApiResponse<Void> createBook(@RequestBody BookCreateRequest request) {
        // 创建图书逻辑
        return ApiResponse.success(null, "图书创建成功");
    }

    @GetMapping("/{isbn}")
    public ApiResponse<BookDetailResponse> getBook(@PathVariable String isbn) {
        // 获取图书详情逻辑
        return ApiResponse.success(new BookDetailResponse(
                null, null, null, null,
                null, null, null, 0
        ));
    }

    @GetMapping
    public ApiResponse<PageResponse<BookSummaryResponse>> listBooks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        // 分页查询图书列表逻辑
        return ApiResponse.success(new PageResponse<>(null, 0, 0, 0));
    }

    @PutMapping("/{isbn}")
    public ApiResponse<Void> updateBook(
            @PathVariable String isbn,
            @RequestBody BookUpdateRequest request) {
        // 更新图书信息逻辑
        return ApiResponse.success(null, "图书更新成功");
    }

    @DeleteMapping("/{isbn}")
    public ApiResponse<Void> deleteBook(@PathVariable String isbn) {
        // 删除图书逻辑
        return ApiResponse.success(null, "图书删除成功");
    }
}
