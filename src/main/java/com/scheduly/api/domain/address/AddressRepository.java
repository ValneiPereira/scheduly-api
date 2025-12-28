package com.scheduly.api.domain.address;

import com.scheduly.api.domain.common.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    Address save(Address address, Long ownerId, String ownerType);

    Optional<Address> findById(Long id);

    List<Address> findByOwner(Long ownerId, String ownerType);

    void delete(Long id);
}
