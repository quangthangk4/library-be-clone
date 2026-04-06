package com.library.recommendation.domain.entity;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.recommendation.domain.valueobject.RatingMetadata;
import com.library.recommendation.domain.valueobject.ReviewId;
import com.library.shared.entity.BaseDomainEntity;
import com.library.user.domain.valueobject.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class BookReview extends BaseDomainEntity {
    private ReviewId id;
    private UserId userId;
    private PublicationId publicationId;
    private RatingMetadata rating; // 1-5 stars
    private Integer helpfulCount; // Number of users who found this review helpful
    private Boolean verifiedBorrow; // User actually borrowed this book

    public void markAsHelpful() {
        if (this.helpfulCount == null) {
            this.helpfulCount = 0;
        }
        this.helpfulCount++;
    }

    public static BookReview createForMapper(
            ReviewId id,
            UserId userId,
            PublicationId publicationId,
            RatingMetadata rating,
            Integer helpfulCount,
            Boolean verifiedBorrow) {
        return new BookReview(id, userId, publicationId, rating, helpfulCount, verifiedBorrow);
    }
}
