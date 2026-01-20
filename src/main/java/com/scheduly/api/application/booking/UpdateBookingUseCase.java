package com.scheduly.api.application.booking;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingRepository;
import com.scheduly.api.domain.booking.BookingStatus;
import com.scheduly.api.domain.department.DepartmentRepository;
import com.scheduly.api.domain.professional.Professional;
import com.scheduly.api.domain.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UpdateBookingUseCase {

    private final BookingRepository bookingRepository;
    private final DepartmentRepository departmentRepository;
    private final ProfessionalRepository professionalRepository;

    @Transactional
    public Booking execute(Long id, Booking updateData) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));

        // Validar se o agendamento pode ser modificado
        validateBookingCanBeUpdated(booking);

        if (updateData.getStatus() != null) {
            booking.setStatus(updateData.getStatus());
        }

        if (updateData.getNotes() != null) {
            booking.setNotes(updateData.getNotes());
        }

        if (updateData.getStartAt() != null) {
            // Validar se a nova data não é no passado
            if (updateData.getStartAt().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Não é possível reagendar para uma data no passado");
            }

            // Re-calculate end time if start time changed
            var department = departmentRepository.findById(booking.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Departamento não encontrado"));

            // Buscar profissional para validar horários
            Professional professional = professionalRepository.findById(booking.getProfessionalId())
                    .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

            booking.setStartAt(updateData.getStartAt());
            booking.setEndAt(booking.getStartAt().plusMinutes(department.getDuration()));

            // Validar regras de negócio para o novo horário
            validateNewSchedule(booking, professional);

            // Validate conflicts for new time
            validateConflicts(booking);
        }

        return bookingRepository.save(booking);
    }

    private void validateBookingCanBeUpdated(Booking booking) {
        // Não permite modificar agendamentos cancelados ou completados
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Não é possível modificar um agendamento cancelado");
        }

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Não é possível modificar um agendamento já completado");
        }
    }

    private void validateNewSchedule(Booking booking, Professional professional) {
        // Validar horário de trabalho do profissional
        validateWorkSchedule(booking, professional);

        // Validar dias de trabalho do profissional
        validateWorkingDays(booking, professional);
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

    private void validateConflicts(Booking booking) {
        var conflicts = bookingRepository.findOverlapping(
                booking.getProfessionalId(),
                booking.getStartAt(),
                booking.getEndAt());

        // Filter out current booking from conflicts
        long conflictCount = conflicts.stream()
                .filter(c -> !c.getId().equals(booking.getId()))
                .filter(c -> c.getStatus() != BookingStatus.CANCELLED) // Ignorar agendamentos cancelados
                .count();

        if (conflictCount > 0) {
            throw new IllegalStateException("O profissional já possui um agendamento neste horário");
        }
    }
}
