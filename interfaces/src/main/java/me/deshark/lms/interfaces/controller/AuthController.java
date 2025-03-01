package me.deshark.lms.interfaces.controller;

import jakarta.servlet.http.HttpServletResponse;
import me.deshark.lms.application.info.AuthToken;
import me.deshark.lms.application.info.UserInfo;
import me.deshark.lms.application.service.AuthAppService;
import me.deshark.lms.common.utils.Result;
import me.deshark.lms.interfaces.dto.ApiResponse;
import me.deshark.lms.interfaces.dto.LoginRequest;
import me.deshark.lms.interfaces.dto.RegisterRequest;
import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<ResultBody<Object>> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        AuthToken tokens = authAppService.login(request.username(), request.password());

        // 1. 将refreshToken写入HttpOnly Cookie
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokens.getRefreshToken())
                .httpOnly(true)
                // 生产环境开启
                .secure(true)
                .sameSite("Strict")
                .maxAge(7 * 24 * 3600)
                .path("/api/auth/refresh")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        // 2. AccessToken通过响应体返回
        ResultBody<Object> resultBody = ResultBody.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(tokens)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resultBody);
    }
}
