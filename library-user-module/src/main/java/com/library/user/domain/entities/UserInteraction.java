package com.library.user.domain.entities;

import com.library.shared.entity.BaseDomainEntity;
import com.library.user.domain.enums.InteractionType;
import com.library.user.domain.valueobject.SearchId;
import com.library.user.domain.valueobject.UserId;
import com.library.user.domain.valueobject.UserInteractionId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInteraction extends BaseDomainEntity {
    private UserInteractionId id;
    private UserId userId;
    private InteractionType interactionType;
    private Instant timestamp;

    public static UserInteraction createForMapper(UserInteractionId id, UserId userId, InteractionType interactionType, Instant timestamp) {
        return new UserInteraction(id, userId, interactionType, timestamp);
    }
}
