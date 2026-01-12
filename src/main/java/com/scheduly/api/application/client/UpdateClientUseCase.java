package com.scheduly.api.application.client;

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
        updateCpf(id, updatedClient, existingClient);
        updatePhone(updatedClient, existingClient);

        // Atualizar endereço apenas se for fornecido
        updateEndereco(updatedClient, existingClient);

        return clientRepository.save(existingClient);
    }

    private static void updateEndereco(Client updatedClient, Client existingClient) {
        if (updatedClient.getAddress() != null) {
            if (existingClient.getAddress() != null) {
                // Atualizar endereço existente (apenas campos não-null)
                var existingAddress = existingClient.getAddress();
                var newAddress = updatedClient.getAddress();

                if (newAddress.getStreet() != null) {
                    existingAddress.setStreet(newAddress.getStreet());
                }
                if (newAddress.getNumber() != null) {
                    existingAddress.setNumber(newAddress.getNumber());
                }
                if (newAddress.getComplement() != null) {
                    existingAddress.setComplement(newAddress.getComplement());
                }
                if (newAddress.getNeighborhood() != null) {
                    existingAddress.setNeighborhood(newAddress.getNeighborhood());
                }
                if (newAddress.getCity() != null) {
                    existingAddress.setCity(newAddress.getCity());
                }
                if (newAddress.getState() != null) {
                    existingAddress.setState(newAddress.getState());
                }
                if (newAddress.getZipCode() != null) {
                    existingAddress.setZipCode(newAddress.getZipCode());
                }
            } else {
                // Criar novo endereço se não existir e foi fornecido
                existingClient.setAddress(updatedClient.getAddress());
            }
        }
    }

    private static void updatePhone(Client updatedClient, Client existingClient) {
        if (updatedClient.getPhone() != null) {
            existingClient.setPhone(updatedClient.getPhone());
        }
    }

    private void updateCpf(Long id, Client updatedClient, Client existingClient) {
        if (updatedClient.getCpf() != null) {
            if (!existingClient.getCpf().equals(updatedClient.getCpf())) {
                if (clientRepository.existsByCpfAndIdNot(updatedClient.getCpf(), id)) {
                    throw new ConflictException("CPF já cadastrado: " + updatedClient.getCpf());
                }
            }
            existingClient.setCpf(updatedClient.getCpf());
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
