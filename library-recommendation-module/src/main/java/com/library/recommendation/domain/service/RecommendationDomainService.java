package com.library.recommendation.domain.service;

import com.library.recommendation.domain.model.Review;
import com.library.recommendation.domain.repository.ReviewRepository;

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
    public double calculateAverageRating(String bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
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
    public boolean hasUserReviewedBook(String userId, String bookId) {
        return reviewRepository.findByUserIdAndBookId(userId, bookId).isPresent();
    }

    /**
     * Validate user can create review
     */
    public void validateCanCreateReview(String userId, String bookId) {
        if (hasUserReviewedBook(userId, bookId)) {
            throw new IllegalStateException("User has already reviewed this book");
        }
    }
}
