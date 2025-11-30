package com.scheduly.api.infrastructure.persistence.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceJpaRepository extends JpaRepository<ServiceEntity, Long> {
}
