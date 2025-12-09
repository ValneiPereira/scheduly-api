package com.scheduly.api.application.usecases.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.exception.ConflictException;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Atualizar cliente existente
 */
@Service
@RequiredArgsConstructor
public class UpdateClientUseCase {

    private final ClientRepository clientRepository;

    @Transactional
    public Client execute(Long id, Client updatedClient) {

        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));


        if (!existingClient.getCpf().equals(updatedClient.getCpf())) {
            if (clientRepository.existsByCpfAndIdNot(updatedClient.getCpf(), id)) {
                throw new ConflictException("CPF já cadastrado: " + updatedClient.getCpf());
            }
        }


        if (!existingClient.getEmail().equals(updatedClient.getEmail())) {
            if (clientRepository.existsByEmailAndIdNot(updatedClient.getEmail(), id)) {
                throw new ConflictException("Email já cadastrado: " + updatedClient.getEmail());
            }
        }


        Client clientToUpdate = Client.builder()
                .id(id)
                .name(updatedClient.getName())
                .email(updatedClient.getEmail())
                .cpf(updatedClient.getCpf())
                .phone(updatedClient.getPhone())
                .address(updatedClient.getAddress())
                .createdAt(existingClient.getCreatedAt())
                .updatedAt(existingClient.getUpdatedAt())
                .build();

        return clientRepository.save(clientToUpdate);
    }
}
