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

        // Atualizar endereço existente ou criar novo
        if (updatedClient.getAddress() != null) {
            if (existingClient.getAddress() != null) {
                // Atualizar endereço existente
                existingClient.getAddress().setStreet(updatedClient.getAddress().getStreet());
                existingClient.getAddress().setNumber(updatedClient.getAddress().getNumber());
                existingClient.getAddress().setComplement(updatedClient.getAddress().getComplement());
                existingClient.getAddress().setNeighborhood(updatedClient.getAddress().getNeighborhood());
                existingClient.getAddress().setCity(updatedClient.getAddress().getCity());
                existingClient.getAddress().setState(updatedClient.getAddress().getState());
                existingClient.getAddress().setZipCode(updatedClient.getAddress().getZipCode());
            } else {
                // Criar novo endereço se não existir
                existingClient.setAddress(updatedClient.getAddress());
            }
        }

        // Atualizar campos do cliente
        existingClient.setName(updatedClient.getName());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setCpf(updatedClient.getCpf());
        existingClient.setPhone(updatedClient.getPhone());

        return clientRepository.save(existingClient);
    }
}
