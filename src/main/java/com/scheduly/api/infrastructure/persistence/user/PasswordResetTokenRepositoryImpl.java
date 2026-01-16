package com.scheduly.api.infrastructure.persistence.user;

import com.scheduly.api.domain.user.PasswordResetToken;
import com.scheduly.api.domain.user.PasswordResetTokenRepository;
import com.scheduly.api.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PasswordResetTokenRepositoryImpl implements PasswordResetTokenRepository {

    private final PasswordResetTokenJpaRepository jpaRepository;

    @Override
    @Transactional
    public PasswordResetToken save(PasswordResetToken token) {
        PasswordResetTokenEntity entity = toEntity(token);
        PasswordResetTokenEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return jpaRepository.findByToken(token).map(this::toDomain);
    }

    @Override
    @Transactional
    public void deleteByUser(User user) {
        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .build();
        jpaRepository.deleteByUser(userEntity);
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        jpaRepository.deleteByToken(token);
    }

    @Override
    @Transactional
    public void deleteExpiredTokens() {
        jpaRepository.deleteExpiredTokens(LocalDateTime.now());
    }

    private PasswordResetTokenEntity toEntity(PasswordResetToken domain) {
        if (domain == null)
            return null;

        UserEntity userEntity = UserEntity.builder()
                .id(domain.getUser().getId())
                .email(domain.getUser().getEmail())
                .password(domain.getUser().getPassword())
                .role(domain.getUser().getRole())
                .ownerId(domain.getUser().getOwnerId())
                .build();

        return PasswordResetTokenEntity.builder()
                .id(domain.getId())
                .token(domain.getToken())
                .user(userEntity)
                .expiryDate(domain.getExpiryDate())
                .used(domain.getUsed() != null ? domain.getUsed() : false)
                .createdAt(domain.getCreatedAt() != null ? domain.getCreatedAt() : LocalDateTime.now())
                .build();
    }

    private PasswordResetToken toDomain(PasswordResetTokenEntity entity) {
        if (entity == null)
            return null;

        User domainUser = User.builder()
                .id(entity.getUser().getId())
                .email(entity.getUser().getEmail())
                .password(entity.getUser().getPassword())
                .role(entity.getUser().getRole())
                .ownerId(entity.getUser().getOwnerId())
                .createdAt(entity.getUser().getCreatedAt())
                .updatedAt(entity.getUser().getUpdatedAt())
                .build();

        return PasswordResetToken.builder()
                .id(entity.getId())
                .token(entity.getToken())
                .user(domainUser)
                .expiryDate(entity.getExpiryDate())
                .used(entity.getUsed())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
