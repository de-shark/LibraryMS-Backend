package me.deshark.lms.infrastructure.mapper;

import me.deshark.lms.infrastructure.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * @author DE_SHARK
 */
@Mapper
public interface UserMapper {

    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE username = #{username})")
    boolean existsByUsername(@Param("username") String username);

    @Select("SELECT * FROM users WHERE username = #{username}")
    Optional<UserDO> findByUsername(@Param("username") String username);

    int insert(UserDO user);
}