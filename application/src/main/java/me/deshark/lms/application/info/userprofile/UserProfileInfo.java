package me.deshark.lms.application.info.userprofile;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Getter
@Builder
public class UserProfileInfo {
    private UUID userId;
    private String username;
    private String email;
    private String college;
    private String major;
    private String studentId;
    private String grade;
    private Integer admissionYear;
    private String className;
    private String degreeType;
}
