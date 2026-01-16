package com.scheduly.api.application.professional;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingFilter;
import com.scheduly.api.domain.booking.BookingRepository;
import com.scheduly.api.domain.booking.BookingStatus;
import com.scheduly.api.domain.exception.ResourceNotFoundException;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import com.scheduly.api.web.dtos.AvailabilityRequest;
import com.scheduly.api.web.dtos.AvailabilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use Case: Consultar disponibilidade de horários de um profissional
 * 
 * Retorna slots de horários disponíveis para agendamento considerando:
 * - Horário de trabalho do profissional
 * - Dias de trabalho (workingDays)
 * - Agendamentos já existentes
 * - Duração do serviço
 */
@Service
@RequiredArgsConstructor
public class GetProfessionalAvailabilityUseCase {

    private final ProfessionalRepository professionalRepository;
    private final BookingRepository bookingRepository;

    private static final int DEFAULT_SLOT_INTERVAL = 30; // minutos
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Transactional(readOnly = true)
    public AvailabilityResponse execute(AvailabilityRequest request) {
        // 1. Buscar profissional
        Professional professional = professionalRepository.findById(request.professionalId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Profissional não encontrado com ID: " + request.professionalId()));

        // 2. Validar se é dia de trabalho
        if (!isWorkingDay(request.date(), professional)) {
            return new AvailabilityResponse(
                    request.professionalId(),
                    request.date(),
                    request.getDurationMinutesOrDefault(),
                    List.of()
            );
        }

        // 3. Buscar agendamentos existentes do profissional na data
        List<Booking> existingBookings = findExistingBookings(request.professionalId(), request.date());

        // 4. Gerar slots disponíveis
        List<String> availableSlots = generateAvailableSlots(
                professional,
                request.date(),
                request.getDurationMinutesOrDefault(),
                existingBookings
        );

        return new AvailabilityResponse(
                request.professionalId(),
                request.date(),
                request.getDurationMinutesOrDefault(),
                availableSlots
        );
    }

    /**
     * Verifica se a data é um dia de trabalho do profissional
     */
    private boolean isWorkingDay(LocalDate date, Professional professional) {
        if (professional.getWorkingDays() == null || professional.getWorkingDays().isEmpty()) {
            // Se não tem dias definidos, assume que trabalha todos os dias
            return true;
        }

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return professional.getWorkingDays().contains(dayOfWeek.name());
    }

    /**
     * Busca agendamentos existentes do profissional na data especificada
     * Considera apenas agendamentos confirmados ou pendentes
     */
    private List<Booking> findExistingBookings(Long professionalId, LocalDate date) {
        BookingFilter filter = BookingFilter.builder()
                .professionalId(professionalId)
                .date(date)
                .build();

        return bookingRepository.findAll(filter).stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED 
                        || booking.getStatus() == BookingStatus.PENDING)
                .sorted(Comparator.comparing(Booking::getStartAt))
                .collect(Collectors.toList());
    }

    /**
     * Gera lista de slots disponíveis considerando:
     * - Horário de trabalho
     * - Intervalos padrão de 30 minutos (mas respeita a duração do serviço)
     * - Duração do serviço
     * - Agendamentos existentes
     */
    private List<String> generateAvailableSlots(
            Professional professional,
            LocalDate date,
            int durationMinutes,
            List<Booking> existingBookings) {

        // Se não tem horário de trabalho definido, não gera slots
        if (professional.getWorkStartTime() == null || professional.getWorkEndTime() == null) {
            return List.of();
        }

        LocalTime workStartTime = professional.getWorkStartTime();
        LocalTime workEndTime = professional.getWorkEndTime();

        List<String> availableSlots = new ArrayList<>();
        LocalTime currentSlot = workStartTime;

        // Gerar slots de DEFAULT_SLOT_INTERVAL em DEFAULT_SLOT_INTERVAL minutos
        // Mas verificar se cada slot cabe no horário considerando a duração do serviço
        int slotInterval = professional.getIntervalMinutes() != null
                ? professional.getIntervalMinutes()
                : DEFAULT_SLOT_INTERVAL;

        while (currentSlot.plusMinutes(durationMinutes).isBefore(workEndTime)
                || currentSlot.plusMinutes(durationMinutes).equals(workEndTime)) {
            
            LocalDateTime slotStart = LocalDateTime.of(date, currentSlot);
            LocalDateTime slotEnd = slotStart.plusMinutes(durationMinutes);

            // Verificar se o slot cabe no horário de trabalho E não conflita com agendamentos
            if (slotEnd.toLocalTime().isBefore(workEndTime) || slotEnd.toLocalTime().equals(workEndTime)) {
                if (!hasConflict(slotStart, slotEnd, existingBookings)) {
                    availableSlots.add(currentSlot.format(TIME_FORMATTER));
                }
            }

            // Avançar para o próximo slot (intervalo padrão de 30 minutos)
            currentSlot = currentSlot.plusMinutes(slotInterval);
            
            // Evitar loop infinito se o intervalo for muito grande
            if (currentSlot.isAfter(workEndTime)) {
                break;
            }
        }

        return availableSlots;
    }

    /**
     * Verifica se um slot conflita com algum agendamento existente
     */
    private boolean hasConflict(LocalDateTime slotStart, LocalDateTime slotEnd, List<Booking> existingBookings) {
        return existingBookings.stream()
                .anyMatch(booking -> {
                    LocalDateTime bookingStart = booking.getStartAt();
                    LocalDateTime bookingEnd = booking.getEndAt() != null 
                            ? booking.getEndAt() 
                            : bookingStart.plusHours(1); // Fallback se não tiver endAt

                    // Conflito se: slot começa antes do agendamento terminar E slot termina depois do agendamento começar
                    return slotStart.isBefore(bookingEnd) && slotEnd.isAfter(bookingStart);
                });
    }
}
