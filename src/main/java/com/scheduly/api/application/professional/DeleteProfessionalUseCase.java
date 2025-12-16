package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteProfessionalUseCase {

    private final ProfessionalRepository repository;

    @Transactional
    public void execute(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Profissional não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}
