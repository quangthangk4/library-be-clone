package com.library.user.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import com.library.user.domain.enums.InteractionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * JPA Entity for user interactions.
 */
@Entity
@Table(name = "user_interactions", indexes = {
    @Index(name = "idx_interaction_user_id", columnList = "userId"),
    @Index(name = "idx_interaction_type", columnList = "interactionType"),
    @Index(name = "idx_interaction_timestamp", columnList = "timestamp")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInteractionEntity extends BaseEntity {

    @Column( nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false, length = 30)
    private InteractionType interactionType;

    @Column(nullable = false)
    private Instant timestamp;
}
