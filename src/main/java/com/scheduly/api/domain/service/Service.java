package com.scheduly.api.domain.service;

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
public class Service {

    private Long id;
    private String name; // Ex: "Manicure Básica"
    private String description; // Descrição detalhada do serviço
    private ServiceCategory category; // Categoria do serviço

    // Preço e duração
    private BigDecimal price; // Preço base do serviço
    private Integer duration; // Duração em minutos

    // Informações adicionais
    private String requirements; // Requisitos ou preparação necessária
    private String materials; // Materiais utilizados
    private Boolean requiresSpecialist; // Se requer profissional especializado

    // Controle
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
