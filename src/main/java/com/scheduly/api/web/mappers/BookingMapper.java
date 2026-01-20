package com.scheduly.api.web.mappers;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.department.DepartmentRepository;
import com.scheduly.api.domain.professional.ProfessionalServiceRepository;
import com.scheduly.api.web.dtos.BookingRequest;
import com.scheduly.api.web.dtos.BookingResponse;
import com.scheduly.api.web.dtos.BookingUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final ProfessionalServiceRepository professionalServiceRepository;
    private final DepartmentRepository departmentRepository;

    public Booking toDomain(BookingRequest request) {
        if (request == null)
            return null;

        return Booking.builder()
                .clientId(request.clientId())
                .professionalId(request.professionalId())
                .departmentId(request.serviceId())
                .startAt(request.startAt())
                .notes(request.notes())
                .build();
    }

    public Booking toDomain(BookingUpdate update) {
        if (update == null)
            return null;

        return Booking.builder()
                .startAt(update.startAt())
                .notes(update.notes())
                .status(update.status())
                .build();
    }

    public BookingResponse toResponse(Booking domain) {
        if (domain == null)
            return null;

        String serviceName = null;
        String serviceCategory = null;
        Integer priceCents = null;

        // Buscar informações do serviço
        if (domain.getProfessionalId() != null && domain.getDepartmentId() != null) {
            // Buscar o preço do ProfessionalService
            var professionalServiceOpt = professionalServiceRepository
                    .findByProfessionalIdAndDepartmentId(domain.getProfessionalId(), domain.getDepartmentId());
            if (professionalServiceOpt.isPresent()) {
                priceCents = professionalServiceOpt.get().getPriceCents();
            }

            // Buscar nome e categoria do Department
            var departmentOpt = departmentRepository.findById(domain.getDepartmentId());
            if (departmentOpt.isPresent()) {
                var department = departmentOpt.get();
                serviceName = department.getName();
                serviceCategory = department.getCategory() != null ? department.getCategory().name() : null;
            }
        }

        return new BookingResponse(
                domain.getId(),
                domain.getClientId(),
                domain.getProfessionalId(),
                domain.getDepartmentId(),
                serviceName,
                serviceCategory,
                priceCents,
                domain.getStartAt(),
                domain.getEndAt(),
                domain.getStatus(),
                domain.getAddressId(),
                domain.getNotes(),
                domain.getCreatedAt());
    }
}
