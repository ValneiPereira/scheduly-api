package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.web.dtos.ClientRequest;
import com.scheduly.api.web.dtos.ClientResponse;
import com.scheduly.api.web.dtos.ClientUpdate;
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
        if (request == null)
            return null;
        return Client.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .address(addressMapper.toDomain(request.address()))
                .build();
    }

    public Client toDomain(ClientUpdate update) {
        if (update == null)
            return null;
        return Client.builder()
                .name(update.name())
                .email(update.email())
                .phone(update.phone())
                .address(update.address() != null ? addressMapper.toDomain(update.address()) : null)
                .build();
    }

    public ClientResponse toResponse(Client client) {
        if (client == null) {
            return null;
        }

        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                addressMapper.toResponse(client.getAddress()),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
}
