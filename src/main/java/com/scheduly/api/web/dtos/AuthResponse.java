package com.scheduly.api.web.dtos;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String email,
        String role,
        Long ownerId
) {
}
