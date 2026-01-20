package com.scheduly.api.application.auth;

import com.scheduly.api.domain.user.RefreshToken;
import com.scheduly.api.domain.user.RefreshTokenRepository;
import com.scheduly.api.infrastructure.auth.JwtProvider;
import com.scheduly.api.web.dtos.AuthResponse;
import com.scheduly.api.web.dtos.RefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse execute(RefreshRequest request) {
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
                    return new AuthResponse(
                            accessToken,
                            requestRefreshToken,
                            user.getEmail(),
                            user.getRole().name(),
                            user.getOwnerId()
                    );
                })
                .orElseThrow(() -> new RuntimeException("Refresh token não encontrado no banco de dados."));
    }
}
