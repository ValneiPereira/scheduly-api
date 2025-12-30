package com.scheduly.api.application.auth;

import com.scheduly.api.domain.entity.RefreshToken;
import com.scheduly.api.infrastructure.auth.JwtProvider;
import com.scheduly.api.infrastructure.persistence.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final JwtProvider jwtProvider;

    /**
     * Cria um novo refresh token para o usuário
     * Remove tokens antigos do mesmo usuário
     */
    @Transactional
    public String createRefreshToken(String email) {
        // Deleta tokens antigos do usuário
        repository.deleteByUserEmail(email);

        // Gera novo token
        String token = jwtProvider.generateRefreshToken(email);

        // Salva no banco
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUserEmail(email);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(30L * 24 * 60 * 60)); // 30 dias
        refreshToken.setCreatedAt(Instant.now());

        repository.save(refreshToken);

        log.info("Refresh token criado para usuário: {}", email);
        return token;
    }

    /**
     * Busca refresh token no banco
     */
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    /**
     * Verifica se o refresh token expirou
     */
    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    /**
     * Remove todos os refresh tokens de um usuário (usado no logout)
     */
    @Transactional
    public void deleteByUserEmail(String email) {
        repository.deleteByUserEmail(email);
        log.info("Refresh tokens removidos para usuário: {}", email);
    }

    /**
     * Remove tokens expirados (pode ser executado periodicamente)
     */
    @Transactional
    public void deleteExpiredTokens() {
        repository.deleteByExpiryDateBefore(Instant.now());
        log.info("Refresh tokens expirados removidos");
    }
}
