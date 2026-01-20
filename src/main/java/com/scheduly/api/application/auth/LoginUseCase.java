package com.scheduly.api.application.auth;

import com.scheduly.api.domain.user.RefreshToken;
import com.scheduly.api.domain.user.RefreshTokenRepository;
import com.scheduly.api.domain.user.User;
import com.scheduly.api.domain.user.UserRepository;
import com.scheduly.api.infrastructure.auth.JwtProvider;
import com.scheduly.api.infrastructure.auth.UserDetailsImpl;
import com.scheduly.api.web.dtos.AuthResponse;
import com.scheduly.api.web.dtos.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public AuthResponse execute(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        String accessToken = jwtProvider.generateAccessToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository
                .findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Cria e salva o Refresh Token
        String refreshTokenStr = UUID
                .randomUUID()
                .toString();
        RefreshToken refreshToken = RefreshToken
                .builder()
                .token(refreshTokenStr)
                .user(user)
                .expiryDate(LocalDateTime
                        .now()
                        .plusDays(7)) // 7 dias de validade
                .build();

        refreshTokenRepository.deleteByUser(user); // Limpa tokens antigos
        refreshTokenRepository.save(refreshToken);

        String role = userDetails
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        return new AuthResponse(
                accessToken,
                refreshTokenStr,
                userDetails.getEmail(),
                role,
                userDetails.getOwnerId()
        );
    }
}
