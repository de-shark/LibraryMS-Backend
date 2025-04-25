package me.deshark.lms.interfaces.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.*;
import java.util.UUID;

@Data
public class BookCommentRequest {
    @NotBlank(message = "用户ID不能为空")
    @JsonProperty("userId")
    private UUID userId;

    @NotBlank(message = "评论内容不能为空")
    @Size(max = 500, message = "评论内容不能超过500字")
    @JsonProperty("content")
    private String content;

    @Min(value = 1, message = "评分最低为1分")
    @Max(value = 5, message = "评分最高为5分")
    @JsonProperty("rating")
    private int rating;
}
