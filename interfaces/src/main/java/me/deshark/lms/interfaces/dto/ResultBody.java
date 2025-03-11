package me.deshark.lms.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
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
}
