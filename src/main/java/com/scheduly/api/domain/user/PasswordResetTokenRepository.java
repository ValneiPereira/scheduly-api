package com.scheduly.api.domain.user;

import java.util.Optional;

public interface PasswordResetTokenRepository {
    PasswordResetToken save(PasswordResetToken token);

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUser(User user);

    void deleteByToken(String token);

    void deleteExpiredTokens();
}
