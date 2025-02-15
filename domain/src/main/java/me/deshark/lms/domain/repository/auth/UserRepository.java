package me.deshark.lms.domain.repository.auth;

import me.deshark.lms.domain.model.auth.entity.AuthUser;


/**
 * @author DE_SHARK
 */
public interface UserRepository {

    // 根据用户名判断用户是否存在
    boolean existsByUsername(String username);

    // 保存用户聚合根
    boolean save(AuthUser authUser);

    // 根据用户名查找用户（用于登录验证）
    AuthUser findByUsername(String username);

    // 可选：根据邮箱查找用户（用于扩展邮箱登录）

}
