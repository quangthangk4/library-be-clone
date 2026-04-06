package com.library.user.domain.entities;

import com.library.shared.entity.BaseDomainEntity;
import com.library.user.domain.enums.NotificationType;
import com.library.user.domain.valueobject.NotificationId;
import com.library.user.domain.valueobject.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification extends BaseDomainEntity {
    private NotificationId id;
    private UserId userId;
    private NotificationType notificationType;
    private String message;
    private boolean isRead;
    private String link;

    public static Notification create(UserId userId, NotificationType type, String message, String link) {
        return new Notification(NotificationId.generate(), userId, type, message, false, link);
    }

    public static Notification createForMapper(NotificationId id, UserId userId, NotificationType type, String message, boolean isRead, String link) {
        return new Notification(id, userId, type, message, isRead, link);
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
