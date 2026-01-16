package com.scheduly.api.application.professional;

import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Buscar perfil do profissional autenticado
 */
@Service
@RequiredArgsConstructor
public class GetMyProfessionalProfileUseCase {

    private final ProfessionalRepository professionalRepository;

    @Transactional(readOnly = true)
    public Professional execute() {
        // Pega o email do usuário autenticado do contexto de segurança
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Busca profissional pelo email
        return professionalRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado para o usuário: " + email));
    }
}
