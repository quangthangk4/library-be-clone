package com.library.user.domain.entities;

import com.library.user.domain.enums.NotificationType;
import com.library.user.domain.valueobject.NotificationId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification {
    private NotificationId id;
    private NotificationType type;
    private Instant createAt;
    private String message;
    private String link;

    public static Notification create(NotificationType type, Instant timestamp, String message, String link) {
        return new Notification(NotificationId.generate(), type,timestamp, message, link);
    }

    public static Notification of(NotificationId id, NotificationType type,Instant timestamp, String message, String link) {
        return new Notification(id, type,timestamp, message, link);
    }
}
