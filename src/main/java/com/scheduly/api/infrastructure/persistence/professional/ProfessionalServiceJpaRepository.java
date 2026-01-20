package com.scheduly.api.infrastructure.persistence.professional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionalServiceJpaRepository extends JpaRepository<ProfessionalServiceEntity, ProfessionalServiceId> {
    List<ProfessionalServiceEntity> findByProfessional_Id(Long professionalId);
    java.util.Optional<ProfessionalServiceEntity> findByProfessional_IdAndDepartment_Id(Long professionalId, Long departmentId);
    void deleteByProfessional_IdAndDepartment_Id(Long professionalId, Long departmentId);
}
