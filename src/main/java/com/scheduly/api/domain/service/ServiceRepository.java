package com.scheduly.api.domain.service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    Service save(Service service);

    Optional<Service> findById(Long id);

    List<Service> findAll();

    List<Service> findByCategory(ServiceCategory category);

    void deleteById(Long id);

    boolean existsById(Long id);
}
