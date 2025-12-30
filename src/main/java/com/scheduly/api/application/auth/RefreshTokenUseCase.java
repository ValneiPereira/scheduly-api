package com.scheduly.api.application.auth;

import com.scheduly.api.domain.exception.UnauthorizedException;
import com.scheduly.api.infrastructure.auth.JwtProvider;
import com.scheduly.api.infrastructure.auth.UserDetailsImpl;
import com.scheduly.api.infrastructure.auth.UserDetailsServiceImpl;
import com.scheduly.api.web.auth.RefreshTokenResponse;
import com.scheduly.api.web.dtos.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public RefreshTokenResponse execute(RefreshTokenRequest request) {
        String requestRefreshToken = request.refreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshToken -> {
                    // Verifica se expirou
                    if (refreshTokenService.isExpired(refreshToken)) {
                        log.warn("Refresh token expirado para usuário: {}", refreshToken.getUserEmail());
                        throw new UnauthorizedException("Refresh token expirado. Faça login novamente.");
                    }

                    // Carrega usuário
                    UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                            .loadUserByUsername(refreshToken.getUserEmail());

                    // Cria autenticação
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // Gera novo access token
                    String newAccessToken = jwtProvider.generateToken(authentication);

                    log.info("Access token renovado para usuário: {}", refreshToken.getUserEmail());
                    return new RefreshTokenResponse(newAccessToken);
                })
                .orElseThrow(() -> {
                    log.warn("Refresh token inválido: {}", requestRefreshToken);
                    return new UnauthorizedException("Refresh token inválido");
                });
    }
}
