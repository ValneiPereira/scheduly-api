package com.scheduly.api.application.professional;

import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProfessionalUseCase {

    private final ProfessionalRepository repository;

    @Transactional
    public Professional execute(Long id, Professional updatedProfessional) {
        Professional existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado com ID: " + id));

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
}
