package me.deshark.lms.interfaces.dto;


public record RegisterRequest(
        String username,
        String email,
        String password
) {
}
