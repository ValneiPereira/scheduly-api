package com.scheduly.api.application.auth;

import com.scheduly.api.domain.exception.BadRequestException;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.user.PasswordResetToken;
import com.scheduly.api.domain.user.PasswordResetTokenRepository;
import com.scheduly.api.domain.user.User;
import com.scheduly.api.domain.user.UserRepository;
import com.scheduly.api.web.dtos.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResetPasswordUseCase {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(ResetPasswordRequest request) {
        // Buscar token
        PasswordResetToken resetToken = tokenRepository.findByToken(request.token())
                .orElseThrow(() -> new ResourceNotFoundException("Token inválido ou expirado"));

        // Validar token
        if (!resetToken.isValid()) {
            throw new BadRequestException("Token inválido ou expirado");
        }

        // Buscar usuário
        User user = resetToken.getUser();
        if (user == null) {
            throw new ResourceNotFoundException("Usuário associado ao token não encontrado");
        }

        // Atualizar senha
        String encodedPassword = passwordEncoder.encode(request.newPassword());
        user.updatePassword(encodedPassword);
        userRepository.save(user);

        // Marcar token como usado
        resetToken.markAsUsed();
        tokenRepository.save(resetToken);

        // Opcional: Deletar token após uso (ou manter para auditoria)
        // tokenRepository.deleteByToken(request.token());

        log.info("Senha redefinida com sucesso para usuário: {}", user.getEmail());
    }

    /**
     * Valida se um token de reset é válido (não expirado e não usado)
     * Usado pelo endpoint de validação antes de mostrar o formulário de reset
     */
    public boolean validateToken(String token) {
        return tokenRepository.findByToken(token)
                .map(PasswordResetToken::isValid)
                .orElse(false);
    }
}
