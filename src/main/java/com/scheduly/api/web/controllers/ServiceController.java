package com.scheduly.api.web.controllers;

import com.scheduly.api.ServicesApi;
import com.scheduly.api.application.service.*;
import com.scheduly.api.domain.service.Service;
import com.scheduly.api.web.dtos.ServiceRequest;
import com.scheduly.api.web.dtos.ServiceResponse;
import com.scheduly.api.web.dtos.ServiceUpdate;
import com.scheduly.api.web.mappers.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para gerenciamento de serviços
 */
@RestController
@RequiredArgsConstructor
public class ServiceController implements ServicesApi {

    private final CreateServiceUseCase createServiceUseCase;
    private final GetServiceUseCase getServiceUseCase;
    private final ListServicesUseCase listServicesUseCase;
    private final UpdateServiceUseCase updateServiceUseCase;
    private final DeleteServiceUseCase deleteServiceUseCase;
    private final ServiceMapper mapper;

    @Override
    public ResponseEntity<ServiceResponse> createService(ServiceRequest request) {
        Service domain = mapper.toDomain(request);
        Service created = createServiceUseCase.execute(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @Override
    public ResponseEntity<ServiceResponse> getService(Long id) {
        Service service = getServiceUseCase.execute(id);
        return ResponseEntity.ok(mapper.toResponse(service));
    }

    @Override
    public ResponseEntity<List<ServiceResponse>> listServices(String category) {
        List<Service> services = listServicesUseCase.execute(category);
        return ResponseEntity.ok(services.stream().map(mapper::toResponse).toList());
    }

    @Override
    public ResponseEntity<ServiceResponse> updateService(Long id, ServiceUpdate request) {
        Service domain = mapper.toDomain(request, id);
        Service updated = updateServiceUseCase.execute(id, domain);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Override
    public ResponseEntity<Void> deleteService(Long id) {
        deleteServiceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
