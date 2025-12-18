package com.scheduly.api.application.service;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.service.Service;
import com.scheduly.api.domain.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetServiceUseCase {

    private final ServiceRepository repository;

    @Transactional(readOnly = true)
    public Service execute(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado com ID: " + id));
    }
}
