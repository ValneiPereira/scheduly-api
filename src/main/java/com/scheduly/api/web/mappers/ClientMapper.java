package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.client.Client;
import com.scheduly.model.ClientCreate;
import com.scheduly.model.ClientResponse;
import com.scheduly.model.ClientUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper para Client
 */
@Component
@RequiredArgsConstructor
public class ClientMapper {

    public Client toDomain(ClientCreate request) {
        return Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .cpf(request.getCpf())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
    }

    public Client toDomain(ClientUpdate request) {
        return Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .cpf(request.getCpf())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
    }

    public ClientResponse toResponse(Client client) {
        return new ClientResponse()
                .id(client.getId())
                .name(client.getName())
                .phone(client.getPhone())
                .email(client.getEmail())
                .cpf(client.getCpf())
                .address(client.getAddress())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt());
    }
}
