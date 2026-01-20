package com.scheduly.api.application.professional;

import com.scheduly.api.domain.department.DepartmentRepository;
import com.scheduly.api.domain.professional.ProfessionalService;
import com.scheduly.api.domain.professional.ProfessionalServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProfessionalServiceUseCase {

    private final ProfessionalServiceRepository professionalServiceRepository;
    private final DepartmentRepository departmentRepository;

    public ProfessionalService execute(Long professionalId, Long departmentId, ProfessionalService update) {
        // Buscar o serviço existente
        ProfessionalService existing = professionalServiceRepository
                .findByProfessionalIdAndDepartmentId(professionalId, departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado para este profissional"));

        // Atualizar preço
        if (update.getPriceCents() != null) {
            existing.setPriceCents(update.getPriceCents());
        }

        // Atualizar duração (se informada, senão usa a padrão do Department)
        if (update.getDurationMinutes() != null) {
            existing.setDurationMinutes(update.getDurationMinutes());
        } else {
            // Se não informado, busca a duração padrão do Department
            var department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
            existing.setDurationMinutes(department.getDuration());
        }

        return professionalServiceRepository.save(existing);
    }
}
