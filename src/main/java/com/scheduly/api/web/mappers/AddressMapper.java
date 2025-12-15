package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.common.Address;
import com.scheduly.api.web.dtos.AddressRequest;
import com.scheduly.api.web.dtos.AddressResponse;
import com.scheduly.api.web.dtos.ViaCepResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper para Address
 */
@Component
public class AddressMapper {

    public Address toDomain(AddressRequest request) {
        if (request == null) return null;

        return Address.builder()
                .street(request.street())
                .number(request.number())
                .complement(request.complement())
                .neighborhood(request.neighborhood())
                .city(request.city())
                .state(request.state())
                .zipCode(request.zipCode())
                .build();
    }

    public AddressResponse toResponse(ViaCepResponse address) {
        if (address == null) return null;

        return new AddressResponse(
                address.getLogradouro(),
                null,
                address.getComplemento(),
                address.getBairro(),
                address.getLocalidade(),
                address.getUf(),
                address.getCep());
    }

    public AddressResponse toResponse(Address address) {
        if (address == null) return null;

        return new AddressResponse(
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode());
    }
}
