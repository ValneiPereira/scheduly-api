package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.exception.ValidationException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UpdateProfessionalUseCase {

    private final ProfessionalRepository repository;

    @Transactional
    public Professional execute(Long id, Professional updatedProfessional) {
        Professional existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado com ID: " + id));

        // Validar horários de trabalho antes de atualizar
        validateWorkSchedule(updatedProfessional);

        existing.setName(updatedProfessional.getName());
        existing.setPhone(updatedProfessional.getPhone());
        existing.setEmail(updatedProfessional.getEmail());
        existing.setCpf(updatedProfessional.getCpf());
        existing.setAddress(updatedProfessional.getAddress());
        existing.setBio(updatedProfessional.getBio());
        existing.setSpecialtyIds(updatedProfessional.getSpecialtyIds());
        existing.setWorkStartTime(updatedProfessional.getWorkStartTime());
        existing.setWorkEndTime(updatedProfessional.getWorkEndTime());
        existing.setWorkingDays(updatedProfessional.getWorkingDays());
        existing.setActive(updatedProfessional.getActive());

        return repository.save(existing);
    }

    private void validateWorkSchedule(Professional professional) {
        LocalTime workStartTime = professional.getWorkStartTime();
        LocalTime workEndTime = professional.getWorkEndTime();

        // Se um horário está definido, o outro também deve estar
        if ((workStartTime != null && workEndTime == null) || 
            (workStartTime == null && workEndTime != null)) {
            throw new ValidationException("Os horários de início e término devem ser ambos informados ou ambos vazios");
        }

        // Se ambos estão definidos, validar que o horário de início é antes do término
        if (workStartTime != null && workEndTime != null) {
            if (!workStartTime.isBefore(workEndTime)) {
                throw new ValidationException("O horário de início deve ser anterior ao horário de término");
            }
        }
    }
}
