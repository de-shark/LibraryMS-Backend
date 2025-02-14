package me.deshark.lms.interfaces.controller;

import me.deshark.lms.application.info.UserInfo;
import me.deshark.lms.common.utils.Result;
import me.deshark.lms.interfaces.dto.ApiResponse;
import me.deshark.lms.interfaces.dto.LoginRequest;
import me.deshark.lms.application.service.AuthAppService;
import me.deshark.lms.interfaces.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthAppService authAppService;

    public AuthController(AuthAppService authAppService) {
        this.authAppService = authAppService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request) {
        UserInfo userInfo = UserInfo.builder()
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .build();
        Result<String, String> result = authAppService.register(userInfo);
        if (result.isOk()) {
            return ResponseEntity.ok(ApiResponse.success(null, "注册成功"));
        } else {
            return new ResponseEntity<>(ApiResponse.error(result.getErr()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request) {
        UserInfo userInfo = UserInfo.builder()
                .username(request.username())
                .password(request.password())
                .build();
        Result<String, String> result = authAppService.login(userInfo);
        if (result.isOk()) {
            return ResponseEntity.ok(ApiResponse.success(result.getOk(), "登录成功"));
        } else {
            return new ResponseEntity<>(ApiResponse.error(result.getErr()), HttpStatus.BAD_REQUEST);
        }
    }
}
