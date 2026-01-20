package com.scheduly.api.application.auth;

import com.scheduly.api.application.professional.CreateProfessionalUseCase;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.user.User;
import com.scheduly.api.domain.user.UserRepository;
import com.scheduly.api.domain.user.UserRole;
import com.scheduly.api.web.dtos.RegisterProfessionalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterProfessionalUseCase {

    private final CreateProfessionalUseCase createProfessionalUseCase;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(RegisterProfessionalRequest request) {
        // 1. Criar o Profissional
        Professional professional = Professional.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .workStartTime(LocalTime.parse(request.workStartTime()))
                .workEndTime(LocalTime.parse(request.workEndTime()))
                .intervalMinutes(request.intervalMinutes() != null ? request.intervalMinutes() : 30)
                .workingDays(request.workingDays())
                .active(true)
                .build();

        Professional savedProfessional = createProfessionalUseCase.execute(professional);

        // 2. Criar as credenciais de Usuário
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.PROFESSIONAL)
                .ownerId(savedProfessional.getId())
                .build();

        userRepository.save(user);
    }
}
