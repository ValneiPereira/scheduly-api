package com.scheduly.api.web.mappers;

import com.scheduly.api.web.dtos.ViaCepResponse;
import com.scheduly.model.Address;
import org.springframework.stereotype.Component;

/**
 * Mapper para Address
 */
@Component
public class AddressMapper {

    public Address toResponse(ViaCepResponse address) {
        if (address == null) {
            return null;
        }

        return new Address()
                .street(address.getLogradouro())
                .complement(address.getComplemento())
                .neighborhood(address.getBairro())
                .city(address.getLocalidade())
                .state(address.getUf())
                .zipCode(address.getCep());
    }
}
