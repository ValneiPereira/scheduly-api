package com.scheduly.api.web.controllers;

import com.scheduly.api.ProfessionalsApi;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.web.dtos.ProfessionalRequest;
import com.scheduly.api.web.dtos.ProfessionalResponse;
import com.scheduly.api.web.mappers.ProfissionalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfessionalController implements ProfessionalsApi {

    private final ProfissionalMapper mapper;

    @Override
    public ResponseEntity<ProfessionalResponse> createProfessional(ProfessionalRequest professionalRequest) {
        Professional professional = mapper.toDomain(professionalRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
