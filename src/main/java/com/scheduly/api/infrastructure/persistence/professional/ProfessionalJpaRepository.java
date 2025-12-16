package com.scheduly.api.infrastructure.persistence.professional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalJpaRepository extends JpaRepository<ProfessionalEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
