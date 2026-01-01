package com.scheduly.api.web.auth;

import com.scheduly.api.application.auth.LoginUseCase;
import com.scheduly.api.application.auth.RefreshTokenUseCase;
import com.scheduly.api.application.auth.RegisterClientUseCase;
import com.scheduly.api.web.dtos.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterClientUseCase registerClientUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginUseCase.execute(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenUseCase.execute(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterClientRequest request) {
        registerClientUseCase.execute(request);
        return ResponseEntity.status(201).build();
    }
}
