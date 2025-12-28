package com.scheduly.api.infrastructure.persistence.address;

import com.scheduly.api.domain.common.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressEntityMapper {

    public Address toDomain(AddressEntity entity) {
        if (entity == null)
            return null;
        return Address.builder()
                .id(entity.getId())
                .street(entity.getStreet())
                .number(entity.getNumber())
                .complement(entity.getComplement())
                .neighborhood(entity.getNeighborhood())
                .city(entity.getCity())
                .state(entity.getState())
                .zipCode(entity.getZipCode())
                .build();
    }

    public AddressEntity toEntity(Address domain) {
        if (domain == null)
            return null;
        return AddressEntity.builder()
                .id(domain.getId())
                .street(domain.getStreet())
                .number(domain.getNumber())
                .complement(domain.getComplement())
                .neighborhood(domain.getNeighborhood())
                .city(domain.getCity())
                .state(domain.getState())
                .zipCode(domain.getZipCode())
                .build();
    }
}
