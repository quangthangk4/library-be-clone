package com.library.recommendation.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for book reviews.
 */
@Entity
@Table(name = "ratings", indexes = {
    @Index(name = "idx_rating_user_id", columnList = "userId"),
    @Index(name = "idx_rating_publication_id", columnList = "publicationId"),
    @Index(name = "idx_rating_star", columnList = "star")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long publicationId;

    @Column(nullable = false)
    private int star;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Builder.Default
    private int helpfulCount = 0;

    @Builder.Default
    private Boolean verifiedBorrow = false;
}
