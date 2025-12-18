package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta detalhada de um serviço")
public record ServiceResponse(
                @Schema(description = "ID único do serviço", example = "1")
                Long id,

                @Schema(description = "Nome do serviço", example = "Manicure Básica")
                String name,

                @Schema(description = "Descrição detalhada do serviço", example = "Corte e pintura básica das unhas")
                String description,

                @Schema(description = "Categoria do serviço", allowableValues = {
                        "BELEZA", "SAUDE", "SERVICOS", "EDUCACAO",
                        "OUTROS"}, example = "BELEZA")
                String category,

                @Schema(description = "Subcategoria do serviço", allowableValues = { "CABELEIREIRO", "BARBEIRO",
                                "ESTETICISTA", "DESIGNER_SOBRANCELHA", "MANICURE", "PSICOLOGO", "FISIOTERAPEUTA",
                                "NUTRICIONISTA", "PERSONAL_TRAINER", "ELETRICISTA", "ENCANADOR", "TECNICO_INFORMATICA",
                                "MONTADOR_MOVEIS", "AULA_PARTICULAR", "PROFESSOR_IDIOMAS", "REFORCO_ESCOLAR", "MENTOR",
                                "OUTROS" }, example = "MANICURE")
                String subcategory,

                @Schema(description = "Duração média em minutos", example = "45")
                Integer durationMinutes,

                @Schema(description = "Preço em centavos", example = "3500")
                Integer priceCents,

                @Schema(description = "Data de criação do registro")
                LocalDateTime createdAt){
}
