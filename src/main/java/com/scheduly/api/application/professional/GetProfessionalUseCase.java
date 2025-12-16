package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetProfessionalUseCase {

    private final ProfessionalRepository repository;

    @Transactional(readOnly = true)
    public Professional execute(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado com ID: " + id));
    }
}
