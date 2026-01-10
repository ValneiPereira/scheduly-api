package com.scheduly.api.web.controllers;

import com.scheduly.api.ProfessionalsApi;
import com.scheduly.api.application.professional.*;
import com.scheduly.api.application.review.CreateReviewUseCase;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.review.ProfessionalReview;
import com.scheduly.api.web.dtos.ProfessionalRequest;
import com.scheduly.api.web.dtos.ProfessionalResponse;
import com.scheduly.api.web.dtos.ReviewRequest;
import com.scheduly.api.web.dtos.ReviewResponse;
import com.scheduly.api.web.mappers.ProfissionalMapper;
import com.scheduly.api.web.mappers.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProfessionalController implements ProfessionalsApi {

    private final CreateProfessionalUseCase createProfessionalUseCase;
    private final GetProfessionalUseCase getProfessionalUseCase;
    private final ListProfessionalsUseCase listProfessionalsUseCase;
    private final UpdateProfessionalUseCase updateProfessionalUseCase;
    private final DeleteProfessionalUseCase deleteProfessionalUseCase;
    private final ProfissionalMapper mapper;
    private final CreateReviewUseCase createReviewUseCase;
    private final ReviewMapper reviewMapper;

    @Override
    public ResponseEntity<ProfessionalResponse> createProfessional(ProfessionalRequest professionalRequest) {
        Professional professional = mapper.toDomain(professionalRequest);
        Professional created = createProfessionalUseCase.execute(professional);
        ProfessionalResponse response = mapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ProfessionalResponse> getProfessional(Long professionalId) {
        Professional professional = getProfessionalUseCase.execute(professionalId);
        return ResponseEntity.ok(mapper.toResponse(professional));
    }

    @Override
    public ResponseEntity<List<ProfessionalResponse>> listProfessionals(Long departmentId) {
        List<Professional> professionals = listProfessionalsUseCase.execute(departmentId);
        List<ProfessionalResponse> response = professionals.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ProfessionalResponse> updateProfessional(Long professionalId,
            ProfessionalRequest professionalRequest) {
        Professional professional = mapper.toDomain(professionalRequest);
        Professional updated = updateProfessionalUseCase.execute(professionalId, professional);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Override
    public ResponseEntity<Void> deleteProfessional(Long professionalId) {
        deleteProfessionalUseCase.execute(professionalId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ReviewResponse> createReview(Long professionalId, ReviewRequest reviewRequest) {
        ProfessionalReview domainReview = reviewMapper.toDomain(reviewRequest, professionalId);
        ProfessionalReview savedReview = createReviewUseCase.execute(domainReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.toResponse(savedReview));
    }
}
