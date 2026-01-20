package com.scheduly.api.web.controllers;

import com.scheduly.api.application.professional.CreateProfessionalServiceUseCase;
import com.scheduly.api.application.professional.ListProfessionalServicesUseCase;
import com.scheduly.api.application.professional.RemoveProfessionalServiceUseCase;
import com.scheduly.api.application.professional.UpdateProfessionalServiceUseCase;
import com.scheduly.api.web.dtos.ProfessionalServiceRequest;
import com.scheduly.api.web.dtos.ProfessionalServiceResponse;
import com.scheduly.api.web.dtos.ProfessionalServiceUpdate;
import com.scheduly.api.web.mappers.ProfessionalServiceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professionals/{professionalId}/services")
@RequiredArgsConstructor
public class ProfessionalServiceController {

    private final CreateProfessionalServiceUseCase createProfessionalServiceUseCase;
    private final ListProfessionalServicesUseCase listProfessionalServicesUseCase;
    private final RemoveProfessionalServiceUseCase removeProfessionalServiceUseCase;
    private final UpdateProfessionalServiceUseCase updateProfessionalServiceUseCase;
    private final ProfessionalServiceMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProfessionalServiceResponse>> list(@PathVariable Long professionalId) {
        var services = listProfessionalServicesUseCase.execute(professionalId)
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(services);
    }

    @PostMapping
    public ResponseEntity<List<ProfessionalServiceResponse>> create(
            @PathVariable Long professionalId,
            @Valid @RequestBody List<ProfessionalServiceRequest> requests
    ) {
        var responses = requests.stream()
                .map(request -> createProfessionalServiceUseCase.execute(
                        mapper.toDomain(professionalId, request)
                ))
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<ProfessionalServiceResponse> update(
            @PathVariable Long professionalId,
            @PathVariable Long departmentId,
            @Valid @RequestBody ProfessionalServiceUpdate update
    ) {
        var updated = updateProfessionalServiceUseCase.execute(
                professionalId,
                departmentId,
                mapper.toDomain(update)
        );
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long professionalId,
            @PathVariable Long departmentId
    ) {
        removeProfessionalServiceUseCase.execute(professionalId, departmentId);
        return ResponseEntity.noContent().build();
    }
}
