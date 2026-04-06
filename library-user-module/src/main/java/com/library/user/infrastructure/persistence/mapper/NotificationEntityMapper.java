package com.library.user.infrastructure.persistence.mapper;

import com.library.user.domain.entities.Notification;
import com.library.user.domain.valueobject.NotificationId;
import com.library.user.domain.valueobject.UserId;
import com.library.user.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between NotificationEntity and Notification domain model.
 */
@Component
public class NotificationEntityMapper {

    /**
     * Convert domain model to entity.
     */
    public NotificationEntity toEntity(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotificationEntity entity = NotificationEntity.builder()
            .userId(notification.getUserId().getValue())
            .notificationType(notification.getNotificationType())
            .message(notification.getMessage())
            .isRead(notification.isRead())
            .link(notification.getLink())
            .build();

        if (notification.getId() != null) {
            entity.setId(notification.getId().getValue());
        }

        return entity;
    }

    /**
     * Convert entity to domain model.
     */
    public Notification toDomainModel(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }

        return Notification.createForMapper(
            NotificationId.of(entity.getId()),
            UserId.of(entity.getUserId()),
            entity.getNotificationType(),
            entity.getMessage(),
            entity.isRead(),
            entity.getLink()
        );
    }
}
