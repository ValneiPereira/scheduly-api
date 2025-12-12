package com.scheduly.api.application.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Use Case: Buscar cliente por ID
 */
@Service
@RequiredArgsConstructor
public class GetClientUseCase {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Client execute(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
    }

    public List<Client> execute(String name) {
        return clientRepository.findByName(name);
    }
}
