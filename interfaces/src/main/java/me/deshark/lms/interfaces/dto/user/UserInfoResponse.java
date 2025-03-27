package me.deshark.lms.interfaces.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DE_SHARK
 */
@Getter
@AllArgsConstructor
public class UserInfoResponse {
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
