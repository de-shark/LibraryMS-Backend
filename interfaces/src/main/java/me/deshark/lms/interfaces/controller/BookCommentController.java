package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.cqrs.comment.AddBookCommentCommand;
import me.deshark.lms.application.cqrs.comment.AddBookCommentCommandHandler;
import me.deshark.lms.interfaces.dto.ResultBody;
import me.deshark.lms.interfaces.dto.comment.BookCommentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/books/{isbn}/comments")
@RequiredArgsConstructor
public class BookCommentController {

    private final AddBookCommentCommandHandler addBookCommentCommandHandler;

    @PostMapping
    public ResponseEntity<ResultBody<Void>> addComment(
            @PathVariable("isbn")  String isbn,
            @RequestBody BookCommentRequest request) {
        
        log.info("Adding comment for book ISBN: {}", isbn);

        AddBookCommentCommand command = new AddBookCommentCommand(
            isbn,
            request.getUserId(),
            request.getContent(),
            request.getRating()
        );

        addBookCommentCommandHandler.handle(command);
        
        return ResponseEntity.ok(
            ResultBody.success("评论添加成功")
        );
    }
}
