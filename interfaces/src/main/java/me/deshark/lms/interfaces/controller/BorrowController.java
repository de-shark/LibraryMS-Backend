package me.deshark.lms.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.cqrs.borrow.command.*;
import me.deshark.lms.interfaces.dto.BorrowRequest;
import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

/**
 * 图书借阅控制器
 * 
 * <p>处理图书借阅、归还、续借等核心业务流程，实现读者与图书资源之间的交互</p>
 * 
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Borrow", description = "图书借阅相关API")
public class BorrowController {

    private final BorrowCommandHandler borrowCommandHandlerImpl;

    @Operation(summary = "借阅图书", description = "读者借阅指定ISBN的图书")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "借阅成功",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "请求参数错误或读者不满足借阅条件",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "图书不存在或无可借副本",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ResultBody<Void>> borrowBook(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "借阅请求",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BorrowRequest.class))
            ) BorrowRequest request) {
        
        // 从安全上下文中获取当前用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        // 创建command对象，设置当前用户名和ISBN
        BorrowCommand command = BorrowCommand.builder()
                .username(username)
                .isbn(request.getIsbn())
                .build();
                
        borrowCommandHandlerImpl.handle(command);
        
        // 构建Location header
        URI location = URI.create("/api/borrows/" + "这里是借阅ID");
        
        return ResponseEntity.created(location)
                .body(ResultBody.success("借阅成功"));
    }

    @Operation(summary = "归还图书", description = "读者归还已借阅的图书")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "归还成功",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "请求参数错误",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "借阅记录不存在",
                    content = @Content
            )
    })
    @PostMapping("/{transactionId}/return")
    public ResponseEntity<ResultBody<Void>> returnBook(
            @Parameter(description = "借阅记录ID", required = true)
            @PathVariable("transactionId") UUID transactionId) {
        ReturnCommand command = new ReturnCommand(transactionId);
        borrowCommandHandlerImpl.handle(command);
        return ResponseEntity.ok(ResultBody.success("归还成功"));
    }

    @Operation(summary = "续借图书", description = "读者续借已借阅的图书")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "续借成功",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "请求参数错误或不满足续借条件",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "借阅记录不存在",
                    content = @Content
            )
    })
    @PostMapping("/{transactionId}/renew")
    public ResponseEntity<ResultBody<Void>> renewBook(
            @Parameter(description = "借阅记录ID", required = true)
            @PathVariable("transactionId") UUID transactionId) {
        RenewCommand command = new RenewCommand(transactionId);
        borrowCommandHandlerImpl.handle(command);
        return ResponseEntity.ok(ResultBody.success("续借成功"));
    }
}
