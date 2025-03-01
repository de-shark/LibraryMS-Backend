package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommand;
import me.deshark.lms.application.cqrs.book.command.CreateBookCommandHandler;
import me.deshark.lms.interfaces.dto.ApiResponse;
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

    @GetMapping("/{isbn}")
    public ApiResponse<Void> getBook(@PathVariable String isbn) {
        // 获取图书详情逻辑
        return ApiResponse.success(null);
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
