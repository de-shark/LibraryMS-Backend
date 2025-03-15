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
import me.deshark.lms.application.cqrs.borrow.query.GetBorrowTransactionQuery;
import me.deshark.lms.application.cqrs.borrow.query.GetBorrowTransactionQueryHandler;
import me.deshark.lms.application.cqrs.borrow.query.ListPatronBorrowsQuery;
import me.deshark.lms.application.cqrs.borrow.query.ListPatronBorrowsQueryHandler;
import me.deshark.lms.application.info.BorrowTransactionInfo;
import me.deshark.lms.application.info.PageResult;
import me.deshark.lms.interfaces.dto.BorrowTransactionResponse;
import me.deshark.lms.interfaces.dto.PageResponse;
import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final GetBorrowTransactionQueryHandler getBorrowTransactionQueryHandler;
    private final ListPatronBorrowsQueryHandler listPatronBorrowsQueryHandler;

    @Operation(summary = "借阅图书", description = "读者借阅指定ISBN的图书")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "借阅成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResultBody.class)
                    )
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
    public ResponseEntity<ResultBody<BorrowTransactionResponse>> borrowBook(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "借阅请求",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BorrowCommand.class))
            ) BorrowCommand command) {
        BorrowTransactionInfo transactionInfo = borrowCommandHandlerImpl.handle(command);
        BorrowTransactionResponse response = convertToResponse(transactionInfo);
        
        // 构建Location header
        URI location = URI.create("/api/borrows/" + response.getTransactionId());
        
        return ResponseEntity.created(location)
                .body(ResultBody.<BorrowTransactionResponse>builder()
                        .data(response)
                        .message("借阅成功")
                        .build());
    }

    @Operation(summary = "归还图书", description = "读者归还已借阅的图书")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "归还成功",
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
        return ResponseEntity.ok(ResultBody.<Void>builder()
                .message("归还成功")
                .build());
    }

    @Operation(summary = "续借图书", description = "读者续借已借阅的图书")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "续借成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResultBody.class)
                    )
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
    public ResponseEntity<ResultBody<BorrowTransactionResponse>> renewBook(
            @Parameter(description = "借阅记录ID", required = true)
            @PathVariable("transactionId") UUID transactionId) {
        RenewCommand command = new RenewCommand(transactionId);
        BorrowTransactionInfo transactionInfo = borrowCommandHandlerImpl.handle(command);
        BorrowTransactionResponse response = convertToResponse(transactionInfo);
        
        return ResponseEntity.ok(ResultBody.<BorrowTransactionResponse>builder()
                .data(response)
                .message("续借成功")
                .build());
    }

    @Operation(summary = "获取借阅详情", description = "查询特定借阅记录的详细信息")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "查询成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResultBody.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "借阅记录不存在",
                    content = @Content
            )
    })
    @GetMapping("/{transactionId}")
    public ResponseEntity<ResultBody<BorrowTransactionResponse>> getBorrowTransaction(
            @Parameter(description = "借阅记录ID", required = true)
            @PathVariable("transactionId") UUID transactionId) {
        GetBorrowTransactionQuery query = new GetBorrowTransactionQuery(transactionId);
        BorrowTransactionInfo transactionInfo = getBorrowTransactionQueryHandler.handle(query);
        BorrowTransactionResponse response = convertToResponse(transactionInfo);
        
        return ResponseEntity.ok(ResultBody.<BorrowTransactionResponse>builder()
                .data(response)
                .build());
    }

    @Operation(summary = "查询读者借阅记录", description = "分页查询指定读者的所有借阅记录")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "查询成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResultBody.class)
                    )
            )
    })
    @GetMapping("/patron/{patronId}")
    public ResponseEntity<ResultBody<PageResponse<BorrowTransactionResponse>>> listPatronBorrows(
            @Parameter(description = "读者ID", required = true)
            @PathVariable("patronId") UUID patronId,
            @Parameter(description = "页码", example = "1")
            @RequestParam(name = "page", defaultValue = "1") int page,
            @Parameter(description = "每页数量", example = "20")
            @RequestParam(name = "size", defaultValue = "20") int size,
            @Parameter(description = "借阅状态", example = "BORROWED")
            @RequestParam(name = "status", required = false) String status) {
        
        ListPatronBorrowsQuery query = new ListPatronBorrowsQuery(patronId, status, page, size);
        PageResult<BorrowTransactionInfo> pageResult = listPatronBorrowsQueryHandler.handle(query);
        
        List<BorrowTransactionResponse> transactions = pageResult.data().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        
        PageResponse<BorrowTransactionResponse> response = PageResponse.of(
                transactions,
                pageResult.currentPage(),
                pageResult.totalPages(),
                pageResult.totalItems()
        );
        
        return ResponseEntity.ok(ResultBody.<PageResponse<BorrowTransactionResponse>>builder()
                .data(response)
                .build());
    }
    
    /**
     * 将领域信息转换为响应DTO
     * 
     * @param info 借阅交易信息
     * @return 响应DTO
     */
    private BorrowTransactionResponse convertToResponse(BorrowTransactionInfo info) {
        return BorrowTransactionResponse.builder()
                .transactionId(info.getTransactionId())
                .bookCopyId(info.getBookCopyId())
                .patronId(info.getPatronId())
                .isbn(info.getIsbn())
                .bookTitle(info.getBookTitle())
                .startDate(info.getStartDate())
                .dueDate(info.getDueDate())
                .endDate(info.getEndDate())
                .status(info.getStatus())
                .build();
    }
}
