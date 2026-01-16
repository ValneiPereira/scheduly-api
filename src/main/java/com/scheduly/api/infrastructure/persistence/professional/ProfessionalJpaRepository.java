package com.scheduly.api.infrastructure.persistence.professional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalJpaRepository extends JpaRepository<ProfessionalEntity, Long> {
    boolean existsByEmail(String email);

    @Query(value = "SELECT DISTINCT p.* FROM professionals p " +
            "INNER JOIN professional_specialties ps ON p.id = ps.professional_id " +
            "WHERE ps.specialty_id = :departmentId", nativeQuery = true)
    List<ProfessionalEntity> findByDepartmentId(@Param("departmentId") Long departmentId);
}
