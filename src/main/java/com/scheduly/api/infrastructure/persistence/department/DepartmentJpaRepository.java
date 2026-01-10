package com.scheduly.api.infrastructure.persistence.department;

import com.scheduly.api.domain.department.DepartmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentJpaRepository extends JpaRepository<DepartmentEntity, Long> {
    List<DepartmentEntity> findByCategory(DepartmentCategory category);
}
