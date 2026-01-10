package com.scheduly.api.application.department;

import com.scheduly.api.domain.department.Department;
import com.scheduly.api.domain.department.DepartmentCategory;
import com.scheduly.api.domain.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListDepartmentsUseCase {

    private final DepartmentRepository repository;

    @Transactional(readOnly = true)
    public List<Department> execute(String categoryName) {
        if (categoriaInformada(categoryName)) {
            return listarPorCategoria(categoryName);
        }
        return repository.findAll();
    }

    private boolean categoriaInformada(String categoryName) {
        return categoryName != null && !categoryName.isBlank();
    }

    private List<Department> listarPorCategoria(String categoryName) {
        try {
            DepartmentCategory category = DepartmentCategory.valueOf(categoryName);
            return repository.findByCategory(category);
        } catch (IllegalArgumentException ex) {
            // Regra de negócio:
            // Categoria inválida não quebra o sistema,
            // apenas retorna lista vazia para o filtro informado
            return List.of();
        }
    }
}
