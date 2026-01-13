package com.scheduly.api.infrastructure.persistence.professional;

import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.domain.professional.Professional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfessionalRepositoryImpl implements ProfessionalRepository {

    private final ProfessionalJpaRepository jpaRepository;
    private final ProfessionalEntityMapper mapper;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Professional save(Professional professional) {
        ProfessionalEntity entity = mapper.toEntity(professional);
        
        // Salvar profissional (endereço será salvo via cascade)
        ProfessionalEntity saved = jpaRepository.save(entity);
        
        // Forçar flush para garantir que o ID do profissional seja gerado
        entityManager.flush();
        
        // Atualizar ownerId e ownerType do endereço após o profissional ter ID
        if (saved.getAddress() != null) {
            saved.getAddress().setOwnerId(saved.getId());
            saved.getAddress().setOwnerType("PROFESSIONAL");
            // O endereço já está gerenciado, então será persistido automaticamente
        }
        
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
    public List<Professional> findByDepartmentId(Long departmentId) {
        return jpaRepository.findByDepartmentId(departmentId).stream()
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
