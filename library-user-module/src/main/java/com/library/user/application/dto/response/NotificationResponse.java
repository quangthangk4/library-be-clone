package com.library.user.application.dto.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {
    private Long userNotificationId;
    private Long notificationId;
    private String type;
    private String title;
    private String message;
    private String link;
    private Long referenceId;
    private boolean isRead;
    private Instant receivedAt;
    private Instant readAt;
}
