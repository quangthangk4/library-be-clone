package com.library.recommendation.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * JPA Entity for book reviews.
 */
@Entity
@Table(name = "book_reviews", indexes = {
    @Index(name = "idx_review_user_id", columnList = "userId"),
    @Index(name = "idx_review_publication_id", columnList = "publicationId"),
    @Index(name = "idx_review_rating", columnList = "rating")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long publicationId;

    @Column(nullable = false)
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Builder.Default
    private int helpfulCount = 0;

    @Builder.Default
    private Boolean verifiedBorrow = false;
}
