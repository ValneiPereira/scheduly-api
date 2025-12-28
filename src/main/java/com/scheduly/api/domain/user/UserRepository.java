package com.scheduly.api.domain.user;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByOwnerIdAndRole(Long ownerId, UserRole role);

    void deleteById(Long id);
}
