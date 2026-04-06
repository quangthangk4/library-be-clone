package com.library.recommendation.infrastructure.persistence.mapper;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.recommendation.domain.entity.BookReview;
import com.library.recommendation.domain.valueobject.RatingMetadata;
import com.library.recommendation.domain.valueobject.ReviewId;
import com.library.recommendation.infrastructure.persistence.entity.BookReviewEntity;
import com.library.user.domain.valueobject.UserId;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between BookReviewEntity and BookReview domain model.
 */
@Component
public class BookReviewEntityMapper {

    /**
     * Convert domain model to entity.
     */
    public BookReviewEntity toEntity(BookReview review) {
        if (review == null) {
            return null;
        }

        BookReviewEntity entity = BookReviewEntity.builder()
            .userId(review.getUserId().getValue())
            .publicationId(review.getPublicationId().getValue())
            .rating(review.getRating().getValue())
            .comment(review.getRating().getComment())
            .helpfulCount(review.getHelpfulCount())
            .verifiedBorrow(review.getVerifiedBorrow())
            .build();

        if (review.getId() != null) {
            entity.setId(review.getId().getValue());
        }

        return entity;
    }

    /**
     * Convert entity to domain model.
     */
    public BookReview toDomainModel(BookReviewEntity entity) {
        if (entity == null) {
            return null;
        }

        return BookReview.createForMapper(
            ReviewId.of(entity.getId()),
            UserId.of(entity.getUserId()),
            PublicationId.of(entity.getPublicationId()),
            RatingMetadata.of(entity.getRating(), entity.getComment()),
            entity.getHelpfulCount(),
            entity.getVerifiedBorrow()
        );
    }
}
