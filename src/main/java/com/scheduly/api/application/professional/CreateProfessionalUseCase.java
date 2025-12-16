package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ConflictException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProfessionalUseCase {

    private final ProfessionalRepository repository;

    @Transactional
    public Professional execute(Professional professional) {
        if (repository.existsByEmail(professional.getEmail())) {
            throw new ConflictException("Já existe um profissional cadastrado com o email: " + professional.getEmail());
        }

        if (repository.existsByCpf(professional.getCpf())) {
            throw new ConflictException("Já existe um profissional cadastrado com o CPF: " + professional.getCpf());
        }

        return repository.save(professional);
    }
}
