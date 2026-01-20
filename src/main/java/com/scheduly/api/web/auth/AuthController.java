package com.scheduly.api.web.auth;

import com.scheduly.api.AuthApi;
import com.scheduly.api.application.auth.ForgotPasswordUseCase;
import com.scheduly.api.application.auth.LoginUseCase;
import com.scheduly.api.application.auth.RefreshTokenUseCase;
import com.scheduly.api.application.auth.RegisterClientUseCase;
import com.scheduly.api.application.auth.ResetPasswordUseCase;
import com.scheduly.api.web.dtos.AuthResponse;
import com.scheduly.api.web.dtos.ForgotPasswordRequest;
import com.scheduly.api.web.dtos.LoginRequest;
import com.scheduly.api.web.dtos.PasswordResetTokenValidationResponse;
import com.scheduly.api.web.dtos.RefreshRequest;
import com.scheduly.api.web.dtos.RegisterClientRequest;
import com.scheduly.api.web.dtos.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final LoginUseCase loginUseCase;
    private final RegisterClientUseCase registerClientUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @Override
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginUseCase.execute(loginRequest));
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(refreshTokenUseCase.execute(refreshRequest));
    }

    @Override
    public ResponseEntity<Void> register(@RequestBody RegisterClientRequest registerClientRequest) {
        registerClientUseCase.execute(registerClientRequest);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        forgotPasswordUseCase.execute(forgotPasswordRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PasswordResetTokenValidationResponse> validateResetPasswordToken(String token) {
        boolean isValid = resetPasswordUseCase.validateToken(token);
        return ResponseEntity.ok(new PasswordResetTokenValidationResponse(isValid));
    }

    @Override
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        resetPasswordUseCase.execute(resetPasswordRequest);
        return ResponseEntity.ok().build();
    }
}

