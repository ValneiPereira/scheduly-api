package com.scheduly.api.infrastructure.persistence.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.infrastructure.persistence.address.AddressEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientEntityMapper {

    private final AddressEntityMapper addressMapper;

    public Client toDomain(ClientEntity entity) {
        if (entity == null) {
            return null;
        }
        return Client.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .cpf(entity.getCpf())
                .phone(entity.getPhone())
                .address(addressMapper.toDomain(entity.getAddress()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ClientEntity toEntity(Client client) {
        if (client == null) {
            return null;
        }
        return ClientEntity.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .cpf(client.getCpf())
                .phone(client.getPhone())
                .address(addressMapper.toEntity(client.getAddress()))
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }

}
