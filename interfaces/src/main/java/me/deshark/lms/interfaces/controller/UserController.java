package me.deshark.lms.interfaces.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deshark.lms.application.cqrs.core.QueryHandler;
import me.deshark.lms.application.cqrs.userprofile.query.GetUserProfileQuery;
import me.deshark.lms.application.info.userprofile.UserProfileInfo;
import me.deshark.lms.common.exception.UserProfileNotFoundException;
import me.deshark.lms.interfaces.converter.UserProfileConverter;
import me.deshark.lms.interfaces.dto.ResultBody;
import me.deshark.lms.interfaces.dto.user.UpdateUserRequest;
import me.deshark.lms.interfaces.dto.user.UserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final QueryHandler<GetUserProfileQuery, UserProfileInfo> handler;

    /**
     * 获取当前登录用户的信息
     * @return ResponseEntity 包含用户信息的响应实体
     */
    @GetMapping("/me")
    public ResponseEntity<ResultBody<UserInfoResponse>> getCurrentUserInfo() {

        // 从安全上下文中获取当前用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getDetails().toString());

        Optional<UserProfileInfo> op = handler.handle(new GetUserProfileQuery(userId));
        UserInfoResponse response = op
                .map(UserProfileConverter.INSTANCE::entityToDto)
                .orElseThrow(() -> new UserProfileNotFoundException(
                        "用户档案不存在: " + userId
                ));

        return ResponseEntity.ok(ResultBody.success(response, "获取成功"));
    }

    /**
     * 更新当前登录用户的信息
     * @param request 用户更新请求的DTO对象
     * @return ResponseEntity 包含操作结果的响应实体
     */
    @PatchMapping("/me")
    public ResponseEntity<ResultBody<Void>> updateUserInfo(@RequestBody UpdateUserRequest request) {
        // TODO 编写中
        return ResponseEntity.ok(ResultBody.success("更新成功"));
    }
}
