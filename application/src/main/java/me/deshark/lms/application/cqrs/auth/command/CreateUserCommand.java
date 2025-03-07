package me.deshark.lms.application.cqrs.auth.command;

/**
 * 用户注册命令
 * 
 * <p>封装用户注册所需的核心信息，用于触发用户注册流程</p>
 * 
 * @param username 用户名，需唯一
 * @param email 用户邮箱，用于验证和通知，需唯一
 * @param rawPassword 未加密的密码
 * 
 * @author DE_SHARK
 */
public record CreateUserCommand(
    String username,
    String rawPassword,
    String email
) {}
