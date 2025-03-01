package me.deshark.lms.interfaces.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * @author DE_SHARK
 * @date 2025/2/14 13:03
 */
@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final int code;
    private final String message;
    private final T data;
    private final Instant timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data, Instant.now());
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null, Instant.now());
    }
}
