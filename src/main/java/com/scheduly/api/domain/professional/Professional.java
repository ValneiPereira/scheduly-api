package com.scheduly.api.domain.professional;

import com.scheduly.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Professional {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;

    // Endereço (local de atendimento)
    private Address address;

    // Informações profissionais
    private String bio; // Biografia/descrição do profissional
    private List<Long> specialtyIds; // IDs dos serviços que oferece
    private BigDecimal rating; // Avaliação média (0-5)
    private Integer totalReviews; // Total de avaliações

    // Horários de trabalho (podem ser expandidos para dias específicos)
    private LocalTime workStartTime; // Horário de início (ex: 09:00)
    private LocalTime workEndTime; // Horário de término (ex: 18:00)
    private Integer breakDuration; // Duração do intervalo em minutos
    private LocalTime breakStartTime; // Horário de início do intervalo

    // Dias de trabalho (pode ser uma lista de dias da semana)
    private List<String> workingDays; // Ex: ["MONDAY", "TUESDAY", "WEDNESDAY"]

    // Controle
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
