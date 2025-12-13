package com.library.user.domain.event;

import com.library.user.domain.valueobject.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Domain event: User created
 */
@Getter
@AllArgsConstructor
public class UserCreatedEvent {
    private final UserId userId;
    private final String username;
    private final String email;
    private final LocalDateTime occurredOn;

    public UserCreatedEvent(UserId userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.occurredOn = LocalDateTime.now();
    }

}
