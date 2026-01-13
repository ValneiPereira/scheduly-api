package com.scheduly.api.web.auth;

import com.scheduly.api.application.auth.ForgotPasswordUseCase;
import com.scheduly.api.application.auth.LoginUseCase;
import com.scheduly.api.application.auth.RefreshTokenUseCase;
import com.scheduly.api.application.auth.RegisterClientUseCase;
import com.scheduly.api.application.auth.ResetPasswordUseCase;
import com.scheduly.api.web.dtos.ForgotPasswordRequest;
import com.scheduly.api.web.dtos.PasswordResetTokenValidationResponse;
import com.scheduly.api.web.dtos.RefreshTokenRequest;
import com.scheduly.api.web.dtos.ResetPasswordRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterClientUseCase registerClientUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

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

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        forgotPasswordUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reset-password/validate")
    public ResponseEntity<PasswordResetTokenValidationResponse> validateResetToken(@RequestParam String token) {
        // Este endpoint valida se o token é válido sem redefinir a senha
        // Útil para o frontend verificar antes de mostrar o formulário de nova senha
        boolean isValid = resetPasswordUseCase.validateToken(token);
        return ResponseEntity.ok(new PasswordResetTokenValidationResponse(isValid));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        resetPasswordUseCase.execute(request);
        return ResponseEntity.ok().build();
    }
}

