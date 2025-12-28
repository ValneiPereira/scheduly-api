package com.scheduly.api.infrastructure.persistence.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressJpaRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findByOwnerIdAndOwnerType(Long ownerId, String ownerType);
}
