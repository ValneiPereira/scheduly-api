package com.scheduly.api.domain.department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    Department save(Department department);

    Optional<Department> findById(Long id);

    List<Department> findAll();

    List<Department> findByCategory(DepartmentCategory category);

    void deleteById(Long id);

    boolean existsById(Long id);
}
