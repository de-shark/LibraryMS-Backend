package me.deshark.lms.interfaces.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 图书借阅控制器
 * 
 * <p>处理图书借阅、归还、续借等核心业务流程</p>
 * 
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
@Tag(name = "Borrow", description = "图书借阅相关API")
public class BorrowController {

}
