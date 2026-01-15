package com.scheduly.api.application.professional;

import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.domain.professional.ProfessionalService;
import com.scheduly.api.domain.professional.ProfessionalServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProfessionalServiceUseCase {

    private final ProfessionalRepository professionalRepository;
    private final ProfessionalServiceRepository professionalServiceRepository;

    public ProfessionalService execute(ProfessionalService professionalService) {
        professionalRepository.findById(professionalService.getProfessionalId())
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        return professionalServiceRepository.save(professionalService);
    }
}
