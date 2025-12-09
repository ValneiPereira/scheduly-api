package com.scheduly.api.web.controllers;

import com.scheduly.api.CepApi;
import com.scheduly.api.domain.common.Address;
import com.scheduly.api.infrastructure.external.CepServiceViaCep;


import com.scheduly.api.infrastructure.external.dto.ViaCepResponse;
import com.scheduly.api.web.mappers.AddressMapper;

import com.scheduly.model.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para consulta de CEP
 * Implementa CepApi gerado pelo OpenAPI
 */
@RestController
@RequiredArgsConstructor
public class CepController implements CepApi {

    private final CepServiceViaCep cepService;
    private final AddressMapper addressMapper;

    @Override
    public ResponseEntity<AddressResponse> lookupCep(String cep) {
        ViaCepResponse address = cepService.findAddressByCep(cep);
        AddressResponse response = addressMapper.toResponse(address);
        return ResponseEntity.ok(response);
    }
}
