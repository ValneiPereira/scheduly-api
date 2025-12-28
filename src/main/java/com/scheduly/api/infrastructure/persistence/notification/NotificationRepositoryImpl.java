package com.scheduly.api.infrastructure.persistence.notification;

import com.scheduly.api.domain.notification.Notification;
import com.scheduly.api.domain.notification.NotificationRepository;
import com.scheduly.api.domain.notification.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository jpaRepository;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = toEntity(notification);
        NotificationEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Notification> findByBookingId(Long bookingId) {
        return jpaRepository.findByBookingId(bookingId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private NotificationEntity toEntity(Notification domain) {
        if (domain == null)
            return null;
        return NotificationEntity.builder()
                .id(domain.getId())
                .bookingId(domain.getBookingId())
                .channel(domain.getChannel())
                .recipient(domain.getRecipient())
                .subject(domain.getSubject())
                .content(domain.getContent())
                .status(domain.getStatus())
                .sentAt(domain.getSentAt())
                .build();
    }

    private Notification toDomain(NotificationEntity entity) {
        if (entity == null)
            return null;
        return Notification.builder()
                .id(entity.getId())
                .bookingId(entity.getBookingId())
                .channel(entity.getChannel())
                .recipient(entity.getRecipient())
                .subject(entity.getSubject())
                .content(entity.getContent())
                .status(entity.getStatus())
                .sentAt(entity.getSentAt())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
