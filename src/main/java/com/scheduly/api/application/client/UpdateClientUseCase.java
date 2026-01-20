package com.scheduly.api.application.client;

import com.scheduly.api.application.common.AddressUpdateHelper;
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

        // Atualizar campos do cliente apenas se forem fornecidos (não-null)
        updateNome(updatedClient, existingClient);
        updateEmail(id, updatedClient, existingClient);
        updatePhone(updatedClient, existingClient);
        updateAvatarUrl(updatedClient, existingClient);

        // Atualizar endereço apenas se for fornecido
        updateEndereco(updatedClient, existingClient);

        return clientRepository.save(existingClient);
    }

    private static void updateEndereco(Client updatedClient, Client existingClient) {
        // Usa o helper para evitar duplicação de código
        var updatedAddress = AddressUpdateHelper.updateAddress(
            updatedClient.getAddress(), 
            existingClient.getAddress()
        );
        existingClient.setAddress(updatedAddress);
    }

    private static void updatePhone(Client updatedClient, Client existingClient) {
        if (updatedClient.getPhone() != null) {
            existingClient.setPhone(updatedClient.getPhone());
        }
    }

    private static void updateAvatarUrl(Client updatedClient, Client existingClient) {
        if (updatedClient.getAvatarUrl() != null) {
            existingClient.setAvatarUrl(updatedClient.getAvatarUrl());
        }
    }


    private void updateEmail(Long id, Client updatedClient, Client existingClient) {
        if (updatedClient.getEmail() != null) {
            if (!existingClient.getEmail().equals(updatedClient.getEmail())) {
                if (clientRepository.existsByEmailAndIdNot(updatedClient.getEmail(), id)) {
                    throw new ConflictException("Email já cadastrado: " + updatedClient.getEmail());
                }
            }
            existingClient.setEmail(updatedClient.getEmail());
        }
    }

    private static void updateNome(Client updatedClient, Client existingClient) {
        if (updatedClient.getName() != null) {
            existingClient.setName(updatedClient.getName());
        }
    }
}
