package com.scheduly.api.domain.professional;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository {
    Professional save(Professional professional);

    Optional<Professional> findById(Long id);

    List<Professional> findAll();

    List<Professional> findByDepartmentId(Long departmentId);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByEmail(String email);
}
