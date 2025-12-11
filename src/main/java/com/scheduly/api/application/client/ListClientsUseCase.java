package com.scheduly.api.application.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use Case: Listar todos os clientes
 */
@Service
@RequiredArgsConstructor
public class ListClientsUseCase {

    private final ClientRepository clientRepository;

    public List<Client> execute() {
        return clientRepository.findAll();
    }
}
