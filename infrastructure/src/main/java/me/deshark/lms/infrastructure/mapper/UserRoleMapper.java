package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.infrastructure.entity.UserRoleDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Mapper
public interface UserRoleMapper {

    @Insert("INSERT INTO user_role (user_role_id, user_id, role) VALUES (#{userRoleId}, #{userId}, #{role})")
    void insert(UserRoleDO userRoleDO);

    @Select("SELECT * FROM user_role WHERE user_id = #{uuid}")
    Optional<UserRoleDO> findByUserId(@Param("uuid") UUID userId);

}
