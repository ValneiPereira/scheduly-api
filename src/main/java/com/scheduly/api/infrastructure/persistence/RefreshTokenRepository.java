package com.scheduly.api.infrastructure.persistence;

import com.scheduly.api.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUserEmail(String userEmail);

    void deleteByExpiryDateBefore(Instant now);
}
