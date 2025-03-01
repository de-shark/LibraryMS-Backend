package me.deshark.lms.interfaces.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author DE_SHARK
 */
@Getter
@Builder
public class ResultBody<T> {
    private final int code;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;

    public ResultBody(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
