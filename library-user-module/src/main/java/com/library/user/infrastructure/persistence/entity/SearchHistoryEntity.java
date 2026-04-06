package com.library.user.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * JPA Entity for user search history.
 */
@Entity
@Table(name = "search_history", indexes = {
    @Index(name = "idx_search_user_id", columnList = "userId"),
    @Index(name = "idx_search_timestamp", columnList = "timestamp")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistoryEntity extends BaseEntity {

    @Column( nullable = false)
    private Long userId;

    @Column( nullable = false, columnDefinition = "TEXT")
    private String searchQuery;

    @Column(nullable = false)
    private Instant timestamp;
}
