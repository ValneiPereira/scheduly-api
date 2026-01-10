package com.scheduly.api.application.department;

import com.scheduly.api.domain.department.Department;
import com.scheduly.api.domain.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateDepartmentUseCase {

    private final DepartmentRepository repository;

    @Transactional
    public Department execute(Department department) {
        validarRegrasDeNegocio(department);
        return repository.save(department);
    }

    private void validarRegrasDeNegocio(Department department) {
        validarPreco(department.getPrice());
    }

    private void validarPreco(BigDecimal price) {
        if (price != null && price.signum() < 0) {
            throw new IllegalArgumentException("O preço do departamento não pode ser negativo");
        }
    }
}
