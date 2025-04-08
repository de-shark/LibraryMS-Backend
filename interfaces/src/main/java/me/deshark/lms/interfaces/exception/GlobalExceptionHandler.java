package me.deshark.lms.interfaces.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.common.exception.auth.TokenExpiredException;
import me.deshark.lms.common.exception.book.BookAlreadyExistsException;
import me.deshark.lms.common.exception.book.BookNotFoundException;
import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author DE_SHARK
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    // 缺少Refresh Token
    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ResultBody<Void>> handleMissingCookie() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResultBody.<Void>builder().error("缺少Refresh Token").build());
    }

    // 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultBody<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(ResultBody.error(e.getMessage()));
    }

    // 处理图书已存在异常
    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ResultBody<Void>> handleBookAlreadyExistsException(BookAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResultBody.<Void>builder().error(e.getMessage()).build());
    }

    // 处理图书未找到异常
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ResultBody<Void>> handleBookNotFoundException(BookNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResultBody.<Void>builder().error(e.getMessage()).build());
    }

    // 500 Internal Server Error
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResultBody<Void>> handleIllegalStateException(IllegalStateException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(ResultBody.error(e.getMessage()));
    }

    // AccessToken过期
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ResultBody<Void>> handleExpiredJwtException(TokenExpiredException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ResultBody.error(e.getMessage()));
    }

    // 处理其他未捕获异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultBody<Void>> handleUnexpectedException(Exception e) {
        log.error("Unexpected error", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResultBody.<Void>builder().error("出现意外错误，请联系管理员").build());
    }
}
