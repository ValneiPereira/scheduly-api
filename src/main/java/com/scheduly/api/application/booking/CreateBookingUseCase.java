package com.scheduly.api.application.booking;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingRepository;
import com.scheduly.api.domain.booking.BookingStatus;
import com.scheduly.api.domain.booking.events.BookingCreatedEvent;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private final ClientRepository clientRepository;
    private final ProfessionalRepository professionalRepository;
    private final DepartmentRepository departmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Booking execute(Booking booking) {
        // 1. Validar se o cliente existe (comunicação com módulo Client)
        clientRepository.findById(booking.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + booking.getClientId()));

        // 2. Validar se o profissional existe (comunicação com módulo Professional)
        Professional professional = professionalRepository.findById(booking.getProfessionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado: " + booking.getProfessionalId()));

        // 3. Validar se o departamento existe e calcular duração (comunicação com módulo Department)
        var department = departmentRepository.findById(booking.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado: " + booking.getDepartmentId()));

        // 4. Calcular horário de término baseado na duração do departamento
        booking.setEndAt(booking.getStartAt().plusMinutes(department.getDuration()));

        // 5. Definir status inicial
        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }

        // 6. Validar regras de negócio do agendamento
        validateBooking(booking, professional);

        // 7. Salvar agendamento
        Booking saved = bookingRepository.save(booking);

        // 8. Publicar evento para outros módulos reagirem (ex: Notification, Analytics)
        eventPublisher.publishEvent(new BookingCreatedEvent(this, saved));

        return saved;
    }

    private void validateBooking(Booking booking, Professional professional) {
        if (booking.getStartAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data de agendamento não pode ser no passado");
        }

        // Validar horário de trabalho do profissional
        validateWorkSchedule(booking, professional);

        // Validar dias de trabalho do profissional
        validateWorkingDays(booking, professional);

        // Check for conflicts
        var conflicts = bookingRepository.findOverlapping(
                booking.getProfessionalId(),
                booking.getStartAt(),
                booking.getEndAt());

        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("O profissional já possui um agendamento neste horário");
        }
    }

    private void validateWorkSchedule(Booking booking, Professional professional) {
        if (professional.getWorkStartTime() == null || professional.getWorkEndTime() == null) {
            // Se o profissional não tem horários definidos, não validamos
            return;
        }

        LocalTime bookingStartTime = booking.getStartAt().toLocalTime();
        LocalTime bookingEndTime = booking.getEndAt().toLocalTime();
        LocalTime workStartTime = professional.getWorkStartTime();
        LocalTime workEndTime = professional.getWorkEndTime();

        // Verificar se o agendamento está dentro do horário de trabalho
        if (bookingStartTime.isBefore(workStartTime) || bookingEndTime.isAfter(workEndTime)) {
            throw new IllegalArgumentException(
                    String.format("O agendamento está fora do horário de trabalho do profissional. " +
                                    "Horário disponível: %s às %s",
                            workStartTime, workEndTime));
        }
    }

    private void validateWorkingDays(Booking booking, Professional professional) {
        if (professional.getWorkingDays() == null || professional.getWorkingDays().isEmpty()) {
            // Se o profissional não tem dias definidos, não validamos
            return;
        }

        DayOfWeek bookingDay = booking.getStartAt().getDayOfWeek();
        String bookingDayName = bookingDay.name();

        if (!professional.getWorkingDays().contains(bookingDayName)) {
            throw new IllegalArgumentException(
                    String.format("O profissional não trabalha no dia %s. " +
                                    "Dias disponíveis: %s",
                            bookingDayName, String.join(", ", professional.getWorkingDays())));
        }
    }
}
