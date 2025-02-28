package me.deshark.lms.interfaces.controller;

import me.deshark.lms.interfaces.dto.ApiResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    public ApiResponse<Void> createBook() {
        // 创建图书逻辑
        return ApiResponse.success(null, "该功能编写中");
    }

    @GetMapping("/{isbn}")
    public ApiResponse<Void> getBook(@PathVariable String isbn) {
        // 获取图书详情逻辑
        return ApiResponse.success(null, "该功能编写中");
    }

    @GetMapping
    public ApiResponse<Void> listBooks() {
        // 分页查询图书列表逻辑
        return ApiResponse.success(null, "该功能编写中");
    }

    @PutMapping("/{isbn}")
    public ApiResponse<Void> updateBook(@PathVariable String isbn) {
        // 更新图书信息逻辑
        return ApiResponse.success(null, "该功能编写中");
    }

    @DeleteMapping("/{isbn}")
    public ApiResponse<Void> deleteBook(@PathVariable String isbn) {
        // 删除图书逻辑
        return ApiResponse.success(null, "该功能编写中");
    }
}
