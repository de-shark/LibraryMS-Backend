package me.deshark.lms.infrastructure.mapper.profile;

import me.deshark.lms.domain.model.userprofile.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface UserProfileMapper {

    @Select("SELECT * FROM user_profile_view WHERE uuid = #{uuid}")
    Optional<UserProfile> selectProfileView(UUID userId);
}
