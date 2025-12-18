package com.scheduly.api.infrastructure.persistence.service;

import com.scheduly.api.domain.service.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceJpaRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByCategory(ServiceCategory category);
}
