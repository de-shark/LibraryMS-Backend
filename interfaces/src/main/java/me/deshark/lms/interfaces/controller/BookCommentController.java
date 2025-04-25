package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.cqrs.comment.AddBookCommentCommand;
import me.deshark.lms.application.cqrs.comment.AddBookCommentCommandHandler;
import me.deshark.lms.application.cqrs.comment.query.GetBookCommentsQuery;
import me.deshark.lms.application.cqrs.comment.query.GetBookCommentsQueryHandler;
import me.deshark.lms.application.info.BookCommentInfo;
import me.deshark.lms.interfaces.dto.ResultBody;
import me.deshark.lms.interfaces.dto.comment.BookCommentRequest;
import me.deshark.lms.interfaces.dto.comment.BookCommentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/books/{isbn}/comments")
@RequiredArgsConstructor
public class BookCommentController {

    private final AddBookCommentCommandHandler addBookCommentCommandHandler;
    private final GetBookCommentsQueryHandler getBookCommentsQueryHandler;

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

    @GetMapping
    public ResponseEntity<ResultBody<List<BookCommentResponse>>> getComments(
            @PathVariable("isbn") String isbn) {

        log.info("Retrieving comments for book ISBN: {}", isbn);

        GetBookCommentsQuery query = new GetBookCommentsQuery(isbn);
        List<BookCommentInfo> commentInfos = getBookCommentsQueryHandler.handle(query)
                .orElse(List.of());

        List<BookCommentResponse> responses = commentInfos.stream()
                .map(info -> BookCommentResponse.builder()
                        .commentId(info.getCommentId())
                        .userId(info.getUserId())
                        .content(info.getContent())
                        .rating(info.getRating())
                        .createdAt(info.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResultBody.success(responses, "获取评论成功"));
    }
}
