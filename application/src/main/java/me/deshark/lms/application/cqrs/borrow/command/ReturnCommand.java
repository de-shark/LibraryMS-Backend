package me.deshark.lms.application.cqrs.borrow.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 归还图书命令
 *
 * <p>包含归还图书所需的借阅记录ID</p>
 *
 * @author DE_SHARK
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnCommand {
    /**
     * 借阅记录ID
     */
    private UUID recordId;
}