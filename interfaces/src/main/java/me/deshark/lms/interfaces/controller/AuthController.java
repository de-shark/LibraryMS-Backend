package me.deshark.lms.interfaces.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.auth.command.CreateUserCommand;
import me.deshark.lms.application.cqrs.auth.command.CreateUserCommandHandler;
import me.deshark.lms.application.info.AuthToken;
import me.deshark.lms.application.service.AuthAppService;
import me.deshark.lms.interfaces.dto.ApiResponse;
import me.deshark.lms.interfaces.dto.LoginRequest;
import me.deshark.lms.interfaces.dto.RegisterRequest;
import me.deshark.lms.interfaces.dto.ResultBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author DE_SHARK
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthAppService authAppService;
    private final CreateUserCommandHandler createUserCommandHandler;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request) {
        CreateUserCommand command = new CreateUserCommand(
                request.username(),
                request.password(),
                request.email()
        );
        createUserCommandHandler.handle(command);

        URI location = URI.create("/api/user");
        return ResponseEntity.created(location)
                .body(ApiResponse.<Void>builder()
                        .message("Registered Successfully")
                        .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResultBody<Object>> refreshToken(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return new ResponseEntity<>(
                    ResultBody.builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("缺少Refresh Token")
                            .build(),
                    HttpStatus.UNAUTHORIZED
            );
        }

        AuthToken newTokens = authAppService.refresh(refreshToken);

        // 1. 将refreshToken写入HttpOnly Cookie
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", newTokens.getRefreshToken())
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
                .data(newTokens)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resultBody);
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
