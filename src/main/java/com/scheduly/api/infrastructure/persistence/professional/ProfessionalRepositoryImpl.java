package com.scheduly.api.infrastructure.persistence.professional;

import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfessionalRepositoryImpl implements ProfessionalRepository {

    private final ProfessionalJpaRepository jpaRepository;
    private final ProfessionalEntityMapper mapper;

    @Override
    public Professional save(Professional professional) {
        ProfessionalEntity entity = mapper.toEntity(professional);
        ProfessionalEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Professional> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Professional> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }
}
