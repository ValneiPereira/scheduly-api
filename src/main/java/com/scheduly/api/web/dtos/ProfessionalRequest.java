package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalTime;
import java.util.List;

public record ProfessionalRequest(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
        String phone,

        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido")
        String email,

        @Valid
        AddressRequest address,

        @Schema(description = "URL do avatar no Cloudinary", example = "https://res.cloudinary.com/...")
        String avatarUrl,

        @Size(max = 500, message = "Bio deve ter no máximo 500 caracteres")
        String bio,

        @NotEmpty(message = "É necessário informar ao menos uma especialidade")
        List<Long> specialtyIds,

        @Schema(type = "string", example = "09:00")
        @NotNull(message = "Horário de início é obrigatório")
        LocalTime workStartTime,

        @Schema(type = "string", example = "19:00")
        @NotNull(message = "Horário de término é obrigatório")
        LocalTime workEndTime,

        @Schema(description = "Intervalo entre atendimentos em minutos", example = "30")
        @NotNull(message = "Intervalo entre atendimentos é obrigatório")
        @Min(value = 1, message = "Intervalo deve ser no mínimo 1 minuto")
        @Max(value = 480, message = "Intervalo deve ser no máximo 480 minutos (8 horas)")
        Integer intervalMinutes,

        @NotEmpty(message = "É necessário informar os dias de trabalho")
        List<String> workingDays,

        @NotNull(message = "Status ativo/inativo é obrigatório")
        Boolean active
) {}
