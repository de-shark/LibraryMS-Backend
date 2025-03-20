package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.cqrs.borrow.command.BorrowCommand;
import me.deshark.lms.application.cqrs.borrow.command.BorrowCommandHandler;
import me.deshark.lms.application.cqrs.borrow.command.RenewCommand;
import me.deshark.lms.application.cqrs.borrow.command.ReturnCommand;
import me.deshark.lms.application.cqrs.borrow.query.BorrowQueryHandler;
import me.deshark.lms.application.cqrs.borrow.query.GetBorrowRecordsQuery;
import me.deshark.lms.application.info.BorrowRecord;
import me.deshark.lms.common.utils.PageData;
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
public class BorrowController {

    private final BorrowCommandHandler borrowCommandHandlerImpl;
    private final BorrowQueryHandler borrowQueryHandlerImpl;

    @PostMapping
    public ResponseEntity<ResultBody<Void>> borrowBook(
            @RequestBody BorrowRequest request) {
        
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

    @PostMapping("/{transactionId}/return")
    public ResponseEntity<ResultBody<Void>> returnBook(
            @PathVariable("transactionId") UUID transactionId) {
        ReturnCommand command = new ReturnCommand(transactionId);
        borrowCommandHandlerImpl.handle(command);
        return ResponseEntity.ok(ResultBody.success("归还成功"));
    }

    @PostMapping("/{transactionId}/renew")
    public ResponseEntity<ResultBody<Void>> renewBook(
            @PathVariable("transactionId") UUID transactionId) {
        RenewCommand command = new RenewCommand(transactionId);
        borrowCommandHandlerImpl.handle(command);
        return ResponseEntity.ok(ResultBody.success("续借成功"));
    }

    /**
     * 获取所有借阅记录
     * @param page 页码，从1开始
     * @param size 每页大小
     * @return 分页的借阅记录
     */
    @GetMapping
    public ResponseEntity<ResultBody<PageData<BorrowRecord>>> getBorrowRecords(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        // 从安全上下文中获取当前用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        GetBorrowRecordsQuery query = new GetBorrowRecordsQuery(username, page, size);
        PageData<BorrowRecord> result = borrowQueryHandlerImpl.handle(query);
        
        return ResponseEntity.ok(ResultBody.success(result, "查询成功"));
    }
}
