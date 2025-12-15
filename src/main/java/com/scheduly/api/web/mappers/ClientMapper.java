package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.web.dtos.ClientRequest;
import com.scheduly.api.web.dtos.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper para Client
 */
@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final AddressMapper addressMapper;

    public Client toDomain(ClientRequest request) {
        return Client.builder()
                .name(request.name())
                .email(request.email())
                .cpf(request.cpf())
                .phone(request.phone())
                .address(addressMapper.toDomain(request.address()))
                .build();
    }

    public ClientResponse toResponse(Client client) {
        if (client == null) {
            return null;
        }

        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getEmail(),
                client.getCpf(),
                addressMapper.toResponse(client.getAddress()),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
}
