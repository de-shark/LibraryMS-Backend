package me.deshark.lms.infrastructure.mapper.profile;

import me.deshark.lms.domain.model.userprofile.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Mapper
public interface UserProfileMapper {

    @Select("SELECT * FROM users WHERE uuid = #{uuid}")
    Optional<UserProfile> selectProfileView(UUID userId);
}
