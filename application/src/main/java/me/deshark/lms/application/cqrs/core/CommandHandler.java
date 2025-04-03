package me.deshark.lms.application.cqrs.core;

/**
 * 命令处理器接口
 */
public interface CommandHandler<C extends Command> {
    /**
     * 处理命令
     * @param command 命令对象
     */
    void handle(C command);
}