package com.scheduly.api.web.controllers;

import com.scheduly.api.CepApi;
import com.scheduly.api.application.cep.CepServiceViaCep;
import com.scheduly.api.web.dtos.AddressResponse;
import com.scheduly.api.web.mappers.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<AddressResponse> lookupCep(@PathVariable String cep) {
        var address = cepService.findAddressByCep(cep);
        return ResponseEntity.ok(addressMapper.toResponse(address));
    }
}
