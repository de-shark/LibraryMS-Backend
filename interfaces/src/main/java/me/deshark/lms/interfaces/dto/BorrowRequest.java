package me.deshark.lms.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author DE_SHARK
 */
@Data
@Schema(description = "借阅请求")
public class BorrowRequest {
    @Schema(description = "图书ISBN", example = "9787111636623")
    private String isbn;
}
