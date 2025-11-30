package com.scheduly.api.infrastructure.persistence.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "services")
public class ServiceEntity {
    @Id
    private Long id;
    // TODO: Implement ServiceEntity
}
