package com.scheduly.api.application.service;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.service.Service;
import com.scheduly.api.domain.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateServiceUseCase {

    private final ServiceRepository repository;

    @Transactional
    public Service execute(Long id, Service updatedService) {
        Service existing = repository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Serviço não encontrado com ID: " + id));

        existing.merge(updatedService);
        existing.validate();

        return repository.save(existing);
    }

}
