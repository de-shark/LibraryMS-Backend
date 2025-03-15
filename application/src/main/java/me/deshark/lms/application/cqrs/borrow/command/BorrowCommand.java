package me.deshark.lms.application.cqrs.borrow.command;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * 借阅图书命令
 *
 * <p>包含读者借阅图书所需的必要信息</p>
 *
 * @author DE_SHARK
 */
@Data
@Builder
public class BorrowCommand {
    /**
     * 读者用户名
     */
    private final String username;

    /**
     * 图书ISBN
     */
    private final String isbn;
}
