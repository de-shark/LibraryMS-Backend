package me.deshark.lms.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ResultBody<T> {
    private String message;
    private String error;
    private T data;
    private Instant timestamp;
}
