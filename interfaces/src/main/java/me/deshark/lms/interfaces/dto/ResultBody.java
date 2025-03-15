package me.deshark.lms.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * 统一响应体
 *
 * <p>用于包装所有API响应，提供统一的响应格式</p>
 *
 * @param <T> 数据类型
 * 
 * @author DE_SHARK
 * @date 2025/2/14 13:03
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "通用响应体")
public class ResultBody<T> {
    @Schema(description = "响应消息", example = "操作成功")
    private String message;

    @Schema(description = "错误信息", nullable = true)
    private String error;

    @Schema(description = "响应数据", implementation = Object.class)
    private T data;

    @Schema(description = "响应时间戳", example = "2025-03-11T09:48:49.716Z")
    private Instant timestamp;

    /**
     * 创建成功响应
     *
     * @param data    业务数据
     * @param message 成功消息
     * @param <T>     数据类型
     * @return 响应体
     */
    public static <T> ResultBody<T> success(T data, String message) {
        return ResultBody.<T>builder()
                .data(data)
                .message(message)
                .build();
    }

    /**
     * 创建成功响应（只带消息）
     *
     * @param message 成功消息
     * @return 响应体
     */
    public static ResultBody<Void> success(String message) {
        return ResultBody.<Void>builder()
                .message(message)
                .build();
    }

    /**
     * 创建错误响应
     *
     * @param error 错误消息
     * @return 响应体
     */
    public static <T> ResultBody<T> error(String error) {
        return ResultBody.<T>builder()
                .error(error)
                .build();
    }
}
