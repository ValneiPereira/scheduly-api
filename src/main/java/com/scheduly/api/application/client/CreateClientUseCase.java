package com.scheduly.api.application.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.exception.ConflictException;
import com.scheduly.api.domain.exception.ValidationException;
import com.scheduly.api.utils.Utils;
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
        // Validar CPF único
        if (clientRepository.existsByCpf(client.getCpf())) {
            throw new ConflictException("CPF já cadastrado: " + client.getCpf());
        }

        // Validar email único
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new ConflictException("Email já cadastrado: " + client.getEmail());
        }

        // Validar formato CPF (básico - apenas dígitos)
        if (!Utils.isValidCpf(client.getCpf())) {
            throw new ValidationException("CPF inválido: " + client.getCpf());
        }

        // Salvar cliente
        return clientRepository.save(client);
    }
}
