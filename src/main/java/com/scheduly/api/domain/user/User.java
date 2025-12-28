package com.scheduly.api.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password;
    private UserRole role;
    private Long ownerId; // ID of the Client or Professional associated with this user
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
