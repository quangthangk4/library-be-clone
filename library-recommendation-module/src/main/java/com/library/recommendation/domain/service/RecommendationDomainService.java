package com.library.recommendation.domain.service;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.recommendation.domain.entity.BookReview;
import com.library.recommendation.domain.repository.ReviewRepository;
import com.library.user.domain.valueobject.UserId;

import java.util.List;

/**
 * Recommendation domain service
 */
public class RecommendationDomainService {
    private final ReviewRepository reviewRepository;

    public RecommendationDomainService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Calculate average rating for a book
     */
    public double calculateAverageRating(PublicationId publicationId) {
        List<BookReview> reviews = reviewRepository.findByPublicationId(publicationId);
        if (reviews.isEmpty()) {
            return 0.0;
        }

        double sum = reviews.stream()
            .mapToInt(review -> review.getRating().getValue())
            .sum();

        return sum / reviews.size();
    }

    /**
     * Check if user has already reviewed a book
     */
    public boolean hasUserReviewedBook(UserId userId, PublicationId publicationId) {
        return reviewRepository.findByUserIdAndPublicationId(userId, publicationId).isPresent();
    }

    /**
     * Validate user can create review
     */
    public void validateCanCreateReview(UserId userId, PublicationId publicationId) {
        if (hasUserReviewedBook(userId, publicationId)) {
            throw new IllegalStateException("User has already reviewed this book");
        }
    }
}
