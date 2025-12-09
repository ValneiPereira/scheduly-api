package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.common.Address;
import com.scheduly.api.infrastructure.external.dto.ViaCepResponse;
import com.scheduly.api.web.dtos.AddressRequest;
import com.scheduly.model.AddressResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper para Address
 */
@Component
public class AddressMapper {

    public Address toDomain(AddressRequest request) {
        if (request == null) {
            return null;
        }

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
        if (address == null) {
            return null;
        }

        var response = new AddressResponse();
        response.setStreet(address.getLogradouro());
        response.setComplement(address.getComplemento());
        response.setNeighborhood(address.getBairro());
        response.setCity(address.getLocalidade());
        response.setState(address.getUf());
        response.setZipCode(address.getCep());
        return response;
    }


    public AddressResponse toAddressResponse(Address address) {
        if (address == null) {
            return null;
        }

        var response = new AddressResponse();
        response.setStreet(address.getStreet());
        response.setNumber(address.getNumber());
        response.setComplement(address.getComplement());
        response.setNeighborhood(address.getNeighborhood());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setZipCode(address.getZipCode());
        return response;
    }
}
