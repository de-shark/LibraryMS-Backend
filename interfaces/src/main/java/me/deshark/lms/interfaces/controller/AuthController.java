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
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<ApiResponse<AuthToken>> refreshToken(
            @CookieValue(name = "refresh_token") String refreshToken,
            HttpServletResponse response
    ) {
        AuthToken newTokens = authAppService.refresh(refreshToken);
        setRefreshTokenCookie(response, newTokens.getRefreshToken());
        return ResponseEntity.ok().body(ApiResponse.<AuthToken>builder().data(newTokens).build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthToken>> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        AuthToken tokens = authAppService.login(request.username(), request.password());
        setRefreshTokenCookie(response, tokens.getRefreshToken());
        return ResponseEntity.ok().body(ApiResponse.<AuthToken>builder().data(tokens).build());
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                // 生产环境开启
                .secure(true)
                .sameSite("Strict")
                .maxAge(7 * 24 * 3600)
                .path("/api/auth/refresh")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }
}
