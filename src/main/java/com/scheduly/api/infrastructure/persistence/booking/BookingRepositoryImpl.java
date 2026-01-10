package com.scheduly.api.infrastructure.persistence.booking;

import com.scheduly.api.domain.booking.Booking;
import com.scheduly.api.domain.booking.BookingFilter;
import com.scheduly.api.domain.booking.BookingRepository;
import com.scheduly.api.infrastructure.persistence.address.AddressJpaRepository;
import com.scheduly.api.infrastructure.persistence.client.ClientJpaRepository;
import com.scheduly.api.infrastructure.persistence.professional.ProfessionalJpaRepository;
import com.scheduly.api.infrastructure.persistence.department.DepartmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingJpaRepository jpaRepository;
    private final BookingEntityMapper mapper;
    private final ClientJpaRepository clientJpaRepository;
    private final ProfessionalJpaRepository professionalJpaRepository;
    private final DepartmentJpaRepository departmentJpaRepository;
    private final AddressJpaRepository addressJpaRepository;

    @Override
    public Booking save(Booking domain) {
        var client = clientJpaRepository.findById(domain.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        var professional = professionalJpaRepository.findById(domain.getProfessionalId())
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));
        var department = departmentJpaRepository.findById(domain.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Departamento não encontrado"));

        var address = domain.getAddressId() != null
                ? addressJpaRepository.findById(domain.getAddressId()).orElse(null)
                : null;

        BookingEntity entity = mapper.toEntity(domain, client, professional, department, address);
        BookingEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Booking> findAll(BookingFilter filter) {
        // Implementation for filtering could be added here using Specification or Query
        // by Example
        // For now, listing all for simplicity as per common pattern in the project
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Booking> findOverlapping(Long professionalId, LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findOverlapping(professionalId, start, end).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
