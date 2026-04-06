package com.library.user.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import com.library.user.domain.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for user notifications.
 */
@Entity
@Table(name = "notifications", indexes = {
    @Index(name = "idx_notification_user_id", columnList = "userId"),
    @Index(name = "idx_notification_type", columnList = "notificationType"),
    @Index(name = "idx_notification_is_read", columnList = "isRead")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType notificationType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    @Builder.Default
    private boolean isRead = false;

    @Column(length = 255)
    private String link;
}
