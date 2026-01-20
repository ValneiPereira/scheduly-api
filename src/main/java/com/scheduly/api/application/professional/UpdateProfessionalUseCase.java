package com.scheduly.api.application.professional;

import com.scheduly.api.application.common.AddressUpdateHelper;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.domain.exception.ConflictException;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.exception.ValidationException;
import com.scheduly.api.domain.professional.Professional;
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

        // Atualizar campos do profissional apenas se forem fornecidos (não-null)
        updateName(updatedProfessional, existing);
        updateEmail(id, updatedProfessional, existing);
        updatePhone(updatedProfessional, existing);
        updateAvatarUrl(updatedProfessional, existing);
        
        // Atualizar endereço apenas se for fornecido
        updateEndereco(updatedProfessional, existing);
        
        updateBio(updatedProfessional, existing);
        updateSpecialtyIds(updatedProfessional, existing);
        updateWorkStartTime(updatedProfessional, existing);
        updateWorkEndTime(updatedProfessional, existing);
        updateWorkingDays(updatedProfessional, existing);
        updateActive(updatedProfessional, existing);

        return repository.save(existing);
    }

    private static void updateEndereco(Professional updatedProfessional, Professional existing) {
        // Usa o helper para evitar duplicação de código
        var updatedAddress = AddressUpdateHelper.updateAddress(
            updatedProfessional.getAddress(), 
            existing.getAddress()
        );
        existing.setAddress(updatedAddress);
    }

    private static void updateName(Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getName() != null) {
            existing.setName(updatedProfessional.getName());
        }
    }

    private void updateEmail(Long id, Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getEmail() != null) {
            if (!existing.getEmail().equals(updatedProfessional.getEmail())) {
                if (repository.existsByEmail(updatedProfessional.getEmail())) {
                    throw new ConflictException("Email já cadastrado: " + updatedProfessional.getEmail());
                }
            }
            existing.setEmail(updatedProfessional.getEmail());
        }
    }

    private static void updatePhone(Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getPhone() != null) {
            existing.setPhone(updatedProfessional.getPhone());
        }
    }

    private static void updateAvatarUrl(Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getAvatarUrl() != null) {
            existing.setAvatarUrl(updatedProfessional.getAvatarUrl());
        }
    }

    private static void updateBio(Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getBio() != null) {
            existing.setBio(updatedProfessional.getBio());
        }
    }

    private static void updateSpecialtyIds(Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getSpecialtyIds() != null) {
            existing.setSpecialtyIds(updatedProfessional.getSpecialtyIds());
        }
    }

    private static void updateWorkStartTime(Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getWorkStartTime() != null) {
            existing.setWorkStartTime(updatedProfessional.getWorkStartTime());
        }
    }

    private static void updateWorkEndTime(Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getWorkEndTime() != null) {
            existing.setWorkEndTime(updatedProfessional.getWorkEndTime());
        }
    }

    private static void updateWorkingDays(Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getWorkingDays() != null) {
            existing.setWorkingDays(updatedProfessional.getWorkingDays());
        }
    }

    private static void updateActive(Professional updatedProfessional, Professional existing) {
        if (updatedProfessional.getActive() != null) {
            existing.setActive(updatedProfessional.getActive());
        }
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
