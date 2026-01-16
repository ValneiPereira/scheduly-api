package com.scheduly.api.infrastructure.persistence.professional;

import com.scheduly.api.infrastructure.persistence.department.DepartmentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "professional_services")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalServiceEntity {

    @EmbeddedId
    private ProfessionalServiceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("professionalId")
    @JoinColumn(name = "professional_id")
    private ProfessionalEntity professional;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("departmentId")
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @Column(nullable = false)
    private Integer priceCents;

    @Column(nullable = false)
    private Integer durationMinutes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
