package me.deshark.lms.application.cqrs.auth.command;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.service.auth.AuthService;
import org.springframework.stereotype.Component;

/**
 * @author DE_SHARK
 */
@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler {
    private final AuthService authService;

    public void handle(CreateUserCommand command) {
        authService.registerUser(
                command.username(),
                command.rawPassword(),
                command.email()
        );
    }
}
