package com.scheduly.api.application.service;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteServiceUseCase {

    private final ServiceRepository repository;

    @Transactional
    public void execute(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Serviço não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}
