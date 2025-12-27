package com.scheduly.api.infrastructure.persistence.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingJpaRepository extends JpaRepository<BookingEntity, Long> {

    @Query("SELECT b FROM BookingEntity b WHERE b.professional.id = :professionalId " +
            "AND b.status != 'CANCELLED' " +
            "AND ((b.startAt < :endAt AND b.endAt > :startAt))")
    List<BookingEntity> findOverlapping(
            @Param("professionalId") Long professionalId,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt);

    // Additional query methods if needed for filtering
}
