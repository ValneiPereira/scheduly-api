package com.scheduly.api.domain.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    private Long id;
    private String name; // Ex: "Manicure Básica"
    private String description; // Descrição detalhada do departamento
    private DepartmentCategory category; // Ex: BELEZA, SAUDE
    private DepartmentSubcategory subcategory; // Enum rigoroso

    // Preço e duração
    private BigDecimal price; // Preço base do departamento
    private Integer duration; // Duração em minutos

    // Informações adicionais
    private String requirements; // Requisitos ou preparação necessária
    private String materials; // Materiais utilizados
    private Boolean requiresSpecialist; // Se requer profissional especializado

    // Controle
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void merge(Department updated) {
        if (updated.name != null) this.name = updated.name;
        if (updated.description != null) this.description = updated.description;
        if (updated.category != null) this.category = updated.category;
        if (updated.subcategory != null) this.subcategory = updated.subcategory;
        if (updated.price != null) this.price = updated.price;
        if (updated.duration != null) this.duration = updated.duration;
        if (updated.requirements != null) this.requirements = updated.requirements;
        if (updated.materials != null) this.materials = updated.materials;
        if (updated.requiresSpecialist != null) this.requiresSpecialist = updated.requiresSpecialist;
        if (updated.active != null) this.active = updated.active;
    }

    public void validate() {
        validarCategoriaESubcategoria();
        validarPreco();
    }

    private void validarCategoriaESubcategoria() {
        if (subcategory != null && category != null
            && !subcategory.belongsTo(category)) {
            throw new IllegalArgumentException("A subcategoria informada não pertence à categoria do departamento");
        }
    }

    private void validarPreco() {
        if (price != null && price.signum() < 0) {
            throw new IllegalArgumentException("O preço do departamento não pode ser negativo");
        }
    }
}
