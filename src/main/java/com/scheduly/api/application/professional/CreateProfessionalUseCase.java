package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ConflictException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.IllegalArgumentException;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class CreateProfessionalUseCase {

    private final ProfessionalRepository repository;

    @Transactional
    public Professional execute(Professional professional) {
        if (repository.existsByEmail(professional.getEmail())) {
            throw new ConflictException("Já existe um profissional cadastrado com o email: " + professional.getEmail());
        }

        if (repository.existsByCpf(professional.getCpf())) {
            throw new ConflictException("Já existe um profissional cadastrado com o CPF: " + professional.getCpf());
        }

        // Validar horários de trabalho se informados
        validateWorkSchedule(professional);

        return repository.save(professional);
    }

    private void validateWorkSchedule(Professional professional) {
        LocalTime workStartTime = professional.getWorkStartTime();
        LocalTime workEndTime = professional.getWorkEndTime();

        // Se apenas um dos horários foi informado, é inválido
        if ((workStartTime != null && workEndTime == null) || (workStartTime == null && workEndTime != null)) {
            throw new IllegalArgumentException("Os horários de início e término de trabalho devem ser informados juntos");
        }

        // Se ambos foram informados, validar que o início é antes do término
        if (workStartTime != null && workEndTime != null) {
            if (!workStartTime.isBefore(workEndTime)) {
                throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de término");
            }
        }
    }
}
