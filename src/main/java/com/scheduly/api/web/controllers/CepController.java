package com.scheduly.api.web.controllers;

import com.scheduly.api.CepApi;
import com.scheduly.api.application.cep.CepServiceViaCep;
import com.scheduly.api.web.mappers.AddressMapper;
import com.scheduly.model.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Address> lookupCep(String cep) {
        var address = cepService.findAddressByCep(cep);
        var response = addressMapper.toResponse(address);
        return ResponseEntity.ok(response);
    }
}
