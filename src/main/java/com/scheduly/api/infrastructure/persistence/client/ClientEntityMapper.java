package com.scheduly.api.infrastructure.persistence.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.infrastructure.persistence.common.AddressEmbeddable;
import com.scheduly.model.Address;
import org.springframework.stereotype.Component;

@Component
public class ClientEntityMapper {

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
                .address(toAddressDomain(entity.getAddress()))
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
                .address(toAddressEmbeddable(client.getAddress()))
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }

    public Address toAddressDomain(AddressEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }

        var address = new Address();
        address.setStreet(embeddable.getStreet());
        address.setNumber(embeddable.getNumber());
        address.setComplement(embeddable.getComplement());
        address.setNeighborhood(embeddable.getNeighborhood());
        address.setCity(embeddable.getCity());
        address.setState(embeddable.getState());
        address.setZipCode(embeddable.getZipCode());
        return address;
    }

    public AddressEmbeddable toAddressEmbeddable(Address address) {
        if (address == null) {
            return null;
        }
        return AddressEmbeddable.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }
}
