package com.library.user.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import com.library.user.domain.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_notifications", indexes = {
        @Index(name = "idx_user_notification_user_id", columnList = "user_id"),
})
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserNotificationEntity extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "notification_id", nullable = false)
    private Long notificationId;

    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private boolean isRead = false;

    protected UserNotificationEntity() {}
}
