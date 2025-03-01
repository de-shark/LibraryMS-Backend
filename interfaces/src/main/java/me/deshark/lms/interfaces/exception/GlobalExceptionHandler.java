package me.deshark.lms.interfaces.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author DE_SHARK
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    // 处理其他未捕获异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultBody<Void>> handleUnexpectedException(Exception e) {
        log.error("Unexpected error", e);
        ResultBody<Void> resultBody = ResultBody.<Void>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(null)
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resultBody);
    }
}
