package com.scheduly.api.application.professional;

import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Atualizar perfil do profissional autenticado
 */
@Service
@RequiredArgsConstructor
public class UpdateMyProfessionalProfileUseCase {

    private final ProfessionalRepository professionalRepository;
    private final UpdateProfessionalUseCase updateProfessionalUseCase;

    @Transactional
    public Professional execute(Professional updatedProfessional) {
        // Pega o email do usuário autenticado do contexto de segurança
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Busca profissional pelo email
        Professional existing = professionalRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado para o usuário: " + email));
        
        // Usa o UpdateProfessionalUseCase existente para atualizar
        return updateProfessionalUseCase.execute(existing.getId(), updatedProfessional);
    }
}
