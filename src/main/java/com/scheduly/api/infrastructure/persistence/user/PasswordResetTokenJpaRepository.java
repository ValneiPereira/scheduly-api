package com.scheduly.api.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenJpaRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
    Optional<PasswordResetTokenEntity> findByToken(String token);

    void deleteByToken(String token);

    void deleteByUser(UserEntity user);

    @Modifying
    @Query("DELETE FROM PasswordResetTokenEntity t WHERE t.expiryDate < :now")
    void deleteExpiredTokens(LocalDateTime now);
}
