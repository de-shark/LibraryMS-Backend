package me.deshark.lms.interfaces.controller;

import me.deshark.lms.application.info.UserInfo;
import me.deshark.lms.common.utils.Result;
import me.deshark.lms.interfaces.dto.LoginRequest;
import me.deshark.lms.interfaces.dto.LoginResponse;
import me.deshark.lms.interfaces.dto.RegisterRequest;
import me.deshark.lms.interfaces.dto.RegisterResponse;
import me.deshark.lms.application.service.AuthAppService;
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
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        UserInfo userInfo = UserInfo.builder()
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .build();
        Result<String, String> result = authAppService.register(userInfo);
        if (result.isOk()) {
            return new RegisterResponse(result.isOk(), result.getOk());
        } else {
            return new RegisterResponse(result.isOk(), result.getErr());
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        UserInfo userInfo = UserInfo.builder()
                .username(request.username())
                .password(request.password())
                .build();
        Result<String, String> result = authAppService.login(userInfo);
        if (result.isOk()) {
            return new LoginResponse(result.isOk(), result.getOk());
        } else {
            return new LoginResponse(result.isOk(), result.getErr());
        }
    }
}
