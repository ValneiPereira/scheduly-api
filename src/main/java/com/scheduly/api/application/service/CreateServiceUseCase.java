package com.scheduly.api.application.service;

import com.scheduly.api.domain.service.Service;
import com.scheduly.api.domain.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CreateServiceUseCase {

    private final ServiceRepository repository;

    @Transactional
    public Service execute(Service service) {
        service.setActive(true);
        validarRegrasDeNegocio(service);
        return repository.save(service);
    }

    private void validarRegrasDeNegocio(Service service) {
        validarPreco(service.getPrice());
    }

    private void validarPreco(BigDecimal price) {
        if (price != null && price.signum() < 0) {
            throw new IllegalArgumentException("O preço do serviço não pode ser negativo");
        }
    }
}
