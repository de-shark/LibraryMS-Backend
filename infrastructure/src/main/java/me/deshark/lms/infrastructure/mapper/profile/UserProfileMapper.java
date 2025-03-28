package me.deshark.lms.infrastructure.mapper.profile;

import me.deshark.lms.domain.model.userprofile.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface UserProfileMapper {

    @Select("""
        SELECT
            uuid AS userId,
            username,
            email,
            college,
            major,
            student_id AS studentId,
            grade,
            admission_year AS admissionYear,
            class_name AS className,
            degree_type AS degreeType
        FROM user_profile_view
        WHERE uuid = #{userId}
        """)
    Optional<UserProfile> selectProfileView(UUID userId);
}
