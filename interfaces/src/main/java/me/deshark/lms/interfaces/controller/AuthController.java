package me.deshark.lms.interfaces.controller;

import me.deshark.lms.application.dto.LoginRequest;
import me.deshark.lms.application.dto.LoginResponse;
import me.deshark.lms.application.dto.RegisterRequest;
import me.deshark.lms.application.dto.RegisterResponse;
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
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        return authAppService.register(registerRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authAppService.login(loginRequest);
    }
}
