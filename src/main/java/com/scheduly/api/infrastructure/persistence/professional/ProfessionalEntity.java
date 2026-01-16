package com.scheduly.api.infrastructure.persistence.professional;

import com.scheduly.api.infrastructure.persistence.address.AddressEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "professionals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 100)
        private String name;

        @Column(unique = true, nullable = false)
        private String email;

    @Column(length = 15)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "primary_address_id")
    private AddressEntity address;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(length = 500)
    private String bio;

        @ElementCollection
        @CollectionTable(name = "professional_specialties", joinColumns = @JoinColumn(name = "professional_id"))
        @Column(name = "specialty_id")
        private List<Long> specialtyIds;

        @Column(precision = 2, scale = 1)
        private BigDecimal rating;

        private Integer totalReviews;

        @Column(nullable = false)
        private LocalTime workStartTime;

        @Column(nullable = false)
        private LocalTime workEndTime;

        @ElementCollection
        @CollectionTable(name = "professional_working_days", joinColumns = @JoinColumn(name = "professional_id"))
        @Column(name = "working_day")
        private List<String> workingDays;

        @Column(nullable = false)
        private Boolean active;

        @CreationTimestamp
        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(nullable = false)
        private LocalDateTime updatedAt;
}
