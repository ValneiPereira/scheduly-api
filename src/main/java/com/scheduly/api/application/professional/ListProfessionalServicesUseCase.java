package com.scheduly.api.application.professional;

import com.scheduly.api.domain.professional.ProfessionalService;
import com.scheduly.api.domain.professional.ProfessionalServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListProfessionalServicesUseCase {
    private final ProfessionalServiceRepository professionalServiceRepository;

    public List<ProfessionalService> execute(Long professionalId) {
        return professionalServiceRepository.findByProfessionalId(professionalId);
    }
}
