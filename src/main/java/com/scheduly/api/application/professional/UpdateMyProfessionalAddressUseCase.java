package com.scheduly.api.application.professional;

import com.scheduly.api.domain.common.Address;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.web.dtos.AddressUpdateRequest;
import com.scheduly.api.web.mappers.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Atualizar apenas o endereço do profissional autenticado
 */
@Service
@RequiredArgsConstructor
public class UpdateMyProfessionalAddressUseCase {

    private final ProfessionalRepository professionalRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public Professional execute(AddressUpdateRequest addressRequest) {
        // Pega o email do usuário autenticado do contexto de segurança
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Busca profissional pelo email
        Professional existing = professionalRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado para o usuário: " + email));
        
        // Converte AddressUpdateRequest para Address
        Address newAddress = addressMapper.toDomain(addressRequest);
        
        // Atualiza o endereço
        existing.setAddress(newAddress);
        
        return professionalRepository.save(existing);
    }
}
