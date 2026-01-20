package com.scheduly.api.infrastructure.persistence.professional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalJpaRepository extends JpaRepository<ProfessionalEntity, Long> {
    boolean existsByEmail(String email);

    @Query(value = "SELECT DISTINCT p.* FROM professionals p " +
            "INNER JOIN professional_specialties ps ON p.id = ps.professional_id " +
            "WHERE ps.specialty_id = :departmentId", nativeQuery = true)
    List<ProfessionalEntity> findByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT p FROM ProfessionalEntity p LEFT JOIN FETCH p.address WHERE p.id = :id")
    Optional<ProfessionalEntity> findByIdWithAddress(@Param("id") Long id);

    @Query("SELECT p FROM ProfessionalEntity p LEFT JOIN FETCH p.address WHERE p.email = :email")
    Optional<ProfessionalEntity> findByEmailWithAddress(@Param("email") String email);
}
