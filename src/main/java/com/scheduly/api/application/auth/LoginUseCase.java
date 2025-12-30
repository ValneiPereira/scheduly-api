package com.scheduly.api.application.auth;

import com.scheduly.api.infrastructure.auth.JwtProvider;
import com.scheduly.api.infrastructure.auth.UserDetailsImpl;
import com.scheduly.api.web.auth.AuthResponse;
import com.scheduly.api.web.auth.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse execute(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        // Gera access token (curto - 15 minutos)
        String accessToken = jwtProvider.generateToken(authentication);

        // Gera refresh token (longo - 30 dias) e salva no banco
        String refreshToken = refreshTokenService.createRefreshToken(userDetails.getEmail());

        return new AuthResponse(accessToken, refreshToken, userDetails.getEmail(), role, userDetails.getOwnerId());
    }
}
