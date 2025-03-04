package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.infrastructure.entity.UserRoleDO;
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

    @Select("SELECT * FROM user_role WHERE user_id = #{uuid}")
    Optional<UserRoleDO> findByUserId(@Param("uuid") UUID userId);

}
