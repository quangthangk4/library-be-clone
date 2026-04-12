package com.library.circulation.domain.entities;

import com.library.circulation.domain.enums.ActivityType;
import com.library.circulation.domain.valueobject.ActivityId;
import com.library.user.domain.valueobject.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Activity {
    private final ActivityId id;
    private final UserId userId;
    private final ActivityType type;
    private final String bookTitle;
    private final Instant createdAt;

    public static Activity createBorrowed(UserId userId, String bookTitle) {
        return new Activity(
                ActivityId.generate(),
                userId,
                ActivityType.BORROWED,
                bookTitle,
                Instant.now()
        );
    }

    public static Activity createReturned(UserId userId, String bookTitle) {
        return new Activity(
                ActivityId.generate(),
                userId,
                ActivityType.RETURNED,
                bookTitle,
                Instant.now()
        );
    }

    public static Activity createDamaged(UserId userId, String bookTitle) {
        return new Activity(
                ActivityId.generate(),
                userId,
                ActivityType.DAMAGED,
                bookTitle,
                Instant.now()
        );
    }
}