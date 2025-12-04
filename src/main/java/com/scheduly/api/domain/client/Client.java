package com.scheduly.api.domain.client;

import com.scheduly.api.domain.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;

    // Endereço
    private Address address;

    // Informações adicionais
    private String allergies; // Alergias (importante para esmaltes hipoalergênicos)
    private String preferences; // Preferências de serviços
    private String notes; // Observações gerais

    // Controle
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
