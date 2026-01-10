package com.scheduly.api.application.booking;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingRepository;
import com.scheduly.api.domain.booking.BookingStatus;
import com.scheduly.api.domain.booking.events.BookingCreatedEvent;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.domain.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Use Case: Criar novo agendamento
 * 
 * Este Use Case demonstra a comunicação entre módulos do Monolito Modular:
 * - Usa ClientRepository (módulo Client) para validar se cliente existe
 * - Usa ProfessionalRepository (módulo Professional) para validar se profissional existe
 * - Usa DepartmentRepository (módulo Department) para calcular duração
 * - Publica evento BookingCreatedEvent para que outros módulos (ex: Notification) reajam
 */
@Service
@RequiredArgsConstructor
public class CreateBookingUseCase {

    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;           // Módulo Client
    private final ProfessionalRepository professionalRepository; // Módulo Professional
    private final DepartmentRepository departmentRepository;    // Módulo Department
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Booking execute(Booking booking) {
        // 1. Validar se o cliente existe (comunicação com módulo Client)
        clientRepository.findById(booking.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + booking.getClientId()));

        // 2. Validar se o profissional existe (comunicação com módulo Professional)
        professionalRepository.findById(booking.getProfessionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado: " + booking.getProfessionalId()));

        // 3. Validar se o departamento existe e calcular duração (comunicação com módulo Department)
        var department = departmentRepository.findById(booking.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado: " + booking.getServiceId()));

        // 4. Calcular horário de término baseado na duração do departamento
        booking.setEndAt(booking.getStartAt().plusMinutes(department.getDuration()));

        // 5. Definir status inicial
        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }

        // 6. Validar regras de negócio do agendamento
        validateBooking(booking);

        // 7. Salvar agendamento
        Booking saved = bookingRepository.save(booking);

        // 8. Publicar evento para outros módulos reagirem (ex: Notification, Analytics)
        eventPublisher.publishEvent(new BookingCreatedEvent(this, saved));

        return saved;
    }

    private void validateBooking(Booking booking) {
        if (booking.getStartAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data de agendamento não pode ser no passado");
        }

        // Check for conflicts
        var conflicts = bookingRepository.findOverlapping(
                booking.getProfessionalId(),
                booking.getStartAt(),
                booking.getEndAt());

        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("O profissional já possui um agendamento neste horário");
        }
    }
}
