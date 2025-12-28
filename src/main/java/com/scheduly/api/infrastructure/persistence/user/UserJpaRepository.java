package com.scheduly.api.infrastructure.persistence.user;

import com.scheduly.api.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByOwnerIdAndRole(Long ownerId, UserRole role);
}
