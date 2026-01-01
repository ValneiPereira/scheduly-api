package com.scheduly.api.infrastructure.persistence.user;

import com.scheduly.api.domain.user.RefreshToken;
import com.scheduly.api.domain.user.RefreshTokenRepository;
import com.scheduly.api.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository jpaRepository;

    @Override
    @Transactional
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity entity = toEntity(refreshToken);
        RefreshTokenEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
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

    private RefreshTokenEntity toEntity(RefreshToken domain) {
        if (domain == null)
            return null;

        UserEntity userEntity = UserEntity.builder()
                .id(domain.getUser().getId())
                .email(domain.getUser().getEmail())
                .password(domain.getUser().getPassword())
                .role(domain.getUser().getRole())
                .ownerId(domain.getUser().getOwnerId())
                .build();

        return RefreshTokenEntity.builder()
                .id(domain.getId())
                .token(domain.getToken())
                .user(userEntity)
                .expiryDate(domain.getExpiryDate())
                .build();
    }

    private RefreshToken toDomain(RefreshTokenEntity entity) {
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

        return RefreshToken.builder()
                .id(entity.getId())
                .token(entity.getToken())
                .user(domainUser)
                .expiryDate(entity.getExpiryDate())
                .build();
    }
}
