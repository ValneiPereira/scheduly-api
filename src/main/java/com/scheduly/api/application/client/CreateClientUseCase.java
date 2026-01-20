package com.scheduly.api.application.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Criar novo cliente
 */
@Service
@RequiredArgsConstructor
public class CreateClientUseCase {

    private final ClientRepository clientRepository;

    @Transactional
    public Client execute(Client client) {
        // Validar email único
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new ConflictException("Email já cadastrado: " + client.getEmail());
        }

        // Salvar cliente
        return clientRepository.save(client);
    }
}
