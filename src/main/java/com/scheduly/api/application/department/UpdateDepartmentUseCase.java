package com.scheduly.api.application.department;

import com.scheduly.api.domain.department.Department;
import com.scheduly.api.domain.department.DepartmentRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateDepartmentUseCase {

    private final DepartmentRepository repository;

    @Transactional
    public Department execute(Long id, Department updatedDepartment) {
        Department existing = repository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Departamento não encontrado com ID: " + id));

        existing.merge(updatedDepartment);
        existing.validate();

        return repository.save(existing);
    }

}
