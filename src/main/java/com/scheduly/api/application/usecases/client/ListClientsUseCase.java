package com.scheduly.api.application.usecases.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Use Case: Listar todos os clientes
 */
@Service
@RequiredArgsConstructor
public class ListClientsUseCase {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<Client> execute() {
        return clientRepository.findAll();
    }
}
