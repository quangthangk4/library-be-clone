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
    @Index(name = "idx_search_user_id", columnList = "userId")
})
@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchHistoryEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "search_query", nullable = false, columnDefinition = "TEXT")
    private String searchQuery;

    protected SearchHistoryEntity() {
        // JPA requires a default constructor
    }
}
