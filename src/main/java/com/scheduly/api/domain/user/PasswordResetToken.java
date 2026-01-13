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
public class PasswordResetToken {
    private Long id;
    private String token;
    private User user;
    private LocalDateTime expiryDate;
    private Boolean used;
    private LocalDateTime createdAt;

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }

    public boolean isValid() {
        return !isExpired() && !Boolean.TRUE.equals(used);
    }

    public void markAsUsed() {
        this.used = true;
    }
}
