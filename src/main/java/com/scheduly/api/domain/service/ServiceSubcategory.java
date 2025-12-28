package com.scheduly.api.domain.service;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.scheduly.api.domain.service.ServiceCategory.*;

@Getter
public enum ServiceSubcategory {
    // BELEZA
    CABELEIREIRO(BELEZA),
    BARBEIRO(BELEZA),
    ESTETICISTA(BELEZA),
    DESIGNER_SOBRANCELHA(BELEZA),
    MANICURE(BELEZA),

    // SAUDE
    PSICOLOGO(SAUDE),
    FISIOTERAPEUTA(SAUDE),
    NUTRICIONISTA(SAUDE),
    PERSONAL_TRAINER(SAUDE),

    // SERVICOS
    ELETRICISTA(SERVICOS),
    ENCANADOR(SERVICOS),
    TECNICO_INFORMATICA(SERVICOS),
    MONTADOR_MOVEIS(SERVICOS),
    PEDREIRO(SERVICOS),

    // EDUCACAO
    AULA_PARTICULAR(EDUCACAO),
    PROFESSOR_IDIOMAS(EDUCACAO),
    REFORCO_ESCOLAR(EDUCACAO),
    MENTOR(EDUCACAO),

    // OUTROS
    OUTROS(ServiceCategory.OUTROS);

    private final ServiceCategory parentCategory;

    ServiceSubcategory(ServiceCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    /**
     * Checks if this subcategory belongs to the given category.
     */
    public boolean belongsTo(ServiceCategory category) {
        return this.parentCategory == category;
    }

    /**
     * Returns all subcategories for a given category.
     */
    public static List<ServiceSubcategory> getByCategory(ServiceCategory category) {
        return Arrays.stream(values())
                .filter(sub -> sub.getParentCategory() == category)
                .collect(Collectors.toList());
    }
}
