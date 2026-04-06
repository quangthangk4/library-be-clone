package com.library.user.infrastructure.persistence.mapper;

import com.library.user.domain.entities.UserInteraction;
import com.library.user.domain.valueobject.UserId;
import com.library.user.domain.valueobject.UserInteractionId;
import com.library.user.infrastructure.persistence.entity.UserInteractionEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between UserInteractionEntity and UserInteraction domain model.
 */
@Component
public class UserInteractionEntityMapper {

    /**
     * Convert domain model to entity.
     */
    public UserInteractionEntity toEntity(UserInteraction interaction) {
        if (interaction == null) {
            return null;
        }

        UserInteractionEntity entity = UserInteractionEntity.builder()
            .userId(interaction.getUserId().getValue())
            .interactionType(interaction.getInteractionType())
            .timestamp(interaction.getTimestamp())
            .build();

        if (interaction.getId() != null) {
            entity.setId(interaction.getId().getValue());
        }

        return entity;
    }

    /**
     * Convert entity to domain model.
     */
    public UserInteraction toDomainModel(UserInteractionEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserInteraction.createForMapper(
            UserInteractionId.of(entity.getId()),
            UserId.of(entity.getUserId()),
            entity.getInteractionType(),
            entity.getTimestamp()
        );
    }
}
