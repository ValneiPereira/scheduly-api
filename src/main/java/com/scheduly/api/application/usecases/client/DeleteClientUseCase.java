package com.scheduly.api.application.usecases.client;

import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Deletar cliente
 */
@Service
@RequiredArgsConstructor
public class DeleteClientUseCase {

    private final ClientRepository clientRepository;

    @Transactional
    public void execute(Long id) {
        // Verificar se cliente existe
        if (!clientRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }

        // Deletar cliente
        clientRepository.deleteById(id);
    }
}
