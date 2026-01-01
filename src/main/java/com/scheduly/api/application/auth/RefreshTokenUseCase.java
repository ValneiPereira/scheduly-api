package com.scheduly.api.application.auth;

import com.scheduly.api.domain.user.RefreshToken;
import com.scheduly.api.domain.user.RefreshTokenRepository;
import com.scheduly.api.infrastructure.auth.JwtProvider;
import com.scheduly.api.web.auth.AuthResponse;
import com.scheduly.api.web.dtos.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse execute(RefreshTokenRequest request) {
        String requestRefreshToken = request.refreshToken();

        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(token -> {
                    if (token.isExpired()) {
                        refreshTokenRepository.deleteByToken(requestRefreshToken);
                        throw new RuntimeException("Refresh token expirado. Por favor, faça login novamente.");
                    }
                    return token;
                })
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtProvider.generateTokenFromUsername(user.getEmail());
                    return AuthResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(requestRefreshToken)
                            .email(user.getEmail())
                            .role(user.getRole().name())
                            .ownerId(user.getOwnerId())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh token não encontrado no banco de dados."));
    }
}
