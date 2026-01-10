package com.scheduly.api.application.department;

import com.scheduly.api.domain.department.DepartmentRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteDepartmentUseCase {

    private final DepartmentRepository repository;

    @Transactional
    public void execute(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Departamento não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}
