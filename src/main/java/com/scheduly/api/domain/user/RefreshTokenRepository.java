package com.scheduly.api.domain.user;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    void deleteByToken(String token);
}
