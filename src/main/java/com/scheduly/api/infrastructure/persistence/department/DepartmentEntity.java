package com.scheduly.api.infrastructure.persistence.department;

import com.scheduly.api.domain.department.DepartmentCategory;
import com.scheduly.api.domain.department.DepartmentSubcategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "departments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DepartmentCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DepartmentSubcategory subcategory;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer duration; // in minutes

    @Column(length = 500)
    private String requirements;

    @Column(length = 500)
    private String materials;

    private Boolean requiresSpecialist;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
