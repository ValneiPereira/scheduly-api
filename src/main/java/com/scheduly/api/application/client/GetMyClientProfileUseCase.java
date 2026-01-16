package com.scheduly.api.application.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Buscar perfil do cliente autenticado
 */
@Service
@RequiredArgsConstructor
public class GetMyClientProfileUseCase {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Client execute() {
        // Pega o email do usuário autenticado do contexto de segurança
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado para o usuário: " + email));
    }
}
