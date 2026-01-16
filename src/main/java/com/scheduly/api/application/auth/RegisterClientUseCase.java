package com.scheduly.api.application.auth;

import com.scheduly.api.application.client.CreateClientUseCase;
import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.user.User;
import com.scheduly.api.domain.user.UserRepository;
import com.scheduly.api.domain.user.UserRole;
import com.scheduly.api.web.auth.RegisterClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterClientUseCase {

    private final CreateClientUseCase createClientUseCase;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(RegisterClientRequest request) {
        // 1. Criar o Cliente (reaproveitando lógica de validação de Email)
        Client client = Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        Client savedClient = createClientUseCase.execute(client);

        // 2. Criar as credenciais de Usuário
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.CLIENT)
                .ownerId(savedClient.getId())
                .build();

        userRepository.save(user);
    }
}
