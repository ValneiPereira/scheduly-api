package com.scheduly.api.infrastructure.persistence.booking;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.infrastructure.persistence.address.AddressEntity;
import com.scheduly.api.infrastructure.persistence.client.ClientEntity;
import com.scheduly.api.infrastructure.persistence.professional.ProfessionalEntity;
import com.scheduly.api.infrastructure.persistence.department.DepartmentEntity;
import org.springframework.stereotype.Component;

@Component
public class BookingEntityMapper {

    public Booking toDomain(BookingEntity entity) {
        if (entity == null)
            return null;

        return Booking.builder()
                .id(entity.getId())
                .clientId(entity.getClient().getId())
                .professionalId(entity.getProfessional().getId())
                .departmentId(entity.getDepartment().getId())
                .addressId(entity.getAddress() != null ? entity.getAddress().getId() : null)
                .startAt(entity.getStartAt())
                .endAt(entity.getEndAt())
                .status(entity.getStatus())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public BookingEntity toEntity(Booking domain, ClientEntity client, ProfessionalEntity professional,
            DepartmentEntity department, AddressEntity address) {
        if (domain == null)
            return null;

        return BookingEntity.builder()
                .id(domain.getId())
                .client(client)
                .professional(professional)
                .department(department)
                .address(address)
                .startAt(domain.getStartAt())
                .endAt(domain.getEndAt())
                .status(domain.getStatus())
                .notes(domain.getNotes())
                .build();
    }
}
