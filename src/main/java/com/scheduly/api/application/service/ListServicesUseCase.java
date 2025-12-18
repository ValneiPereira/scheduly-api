package com.scheduly.api.application.service;

import com.scheduly.api.domain.service.Service;
import com.scheduly.api.domain.service.ServiceCategory;
import com.scheduly.api.domain.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListServicesUseCase {

    private final ServiceRepository repository;

    @Transactional(readOnly = true)
    public List<Service> execute(String categoryName) {
        if (categoriaInformada(categoryName)) {
            return listarPorCategoria(categoryName);
        }
        return repository.findAll();
    }

    private boolean categoriaInformada(String categoryName) {
        return categoryName != null && !categoryName.isBlank();
    }

    private List<Service> listarPorCategoria(String categoryName) {
        try {
            ServiceCategory category = ServiceCategory.valueOf(categoryName);
            return repository.findByCategory(category);
        } catch (IllegalArgumentException ex) {
            // Regra de negócio:
            // Categoria inválida não quebra o sistema,
            // apenas retorna lista vazia para o filtro informado
            return List.of();
        }
    }
}
