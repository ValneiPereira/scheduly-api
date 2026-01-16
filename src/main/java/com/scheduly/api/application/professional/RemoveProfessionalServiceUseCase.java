package com.scheduly.api.application.professional;

import com.scheduly.api.domain.professional.ProfessionalServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveProfessionalServiceUseCase {
    private final ProfessionalServiceRepository professionalServiceRepository;

    public void execute(Long professionalId, Long departmentId) {
        professionalServiceRepository.deleteByProfessionalIdAndDepartmentId(professionalId, departmentId);
    }
}
