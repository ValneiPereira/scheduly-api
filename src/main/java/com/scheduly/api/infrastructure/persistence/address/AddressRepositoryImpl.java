package com.scheduly.api.infrastructure.persistence.address;

import com.scheduly.api.domain.address.AddressRepository;
import com.scheduly.api.domain.common.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final AddressJpaRepository jpaRepository;

    @Override
    public Address save(Address address, Long ownerId, String ownerType) {
        AddressEntity entity = toEntity(address);
        entity.setOwnerId(ownerId);
        entity.setOwnerType(ownerType);
        AddressEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Address> findByOwner(Long ownerId, String ownerType) {
        return jpaRepository.findByOwnerIdAndOwnerType(ownerId, ownerType)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    private AddressEntity toEntity(Address domain) {
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

    private Address toDomain(AddressEntity entity) {
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
}
