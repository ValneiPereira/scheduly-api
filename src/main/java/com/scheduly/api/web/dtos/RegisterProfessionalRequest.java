package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para registro de profissional")
public record RegisterProfessionalRequest(
        @Schema(description = "Nome completo do profissional", example = "João Silva")
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 1, max = 255, message = "Nome deve ter entre 1 e 255 caracteres")
        String name,

        @Schema(description = "Email do profissional", example = "joao@email.com")
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Schema(description = "Telefone do profissional", example = "11987654321")
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
        String phone,

        @Schema(description = "Senha do profissional", example = "senha123")
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        String password,

        @Schema(description = "Horário de início do trabalho", example = "09:00")
        @NotNull(message = "Horário de início é obrigatório")
        String workStartTime,

        @Schema(description = "Horário de término do trabalho", example = "18:00")
        @NotNull(message = "Horário de término é obrigatório")
        String workEndTime,

        @Schema(description = "Intervalo entre atendimentos em minutos", example = "30")
        Integer intervalMinutes,

        @Schema(description = "Dias de trabalho", example = "[\"MONDAY\", \"TUESDAY\", \"WEDNESDAY\", \"THURSDAY\", \"FRIDAY\"]")
        @NotNull(message = "Dias de trabalho são obrigatórios")
        java.util.List<String> workingDays
) {
}
