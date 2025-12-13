package com.library.recommendation.domain.repository;

import com.library.recommendation.domain.model.Review;
import com.library.recommendation.domain.valueobject.ReviewId;

import java.util.List;
import java.util.Optional;

/**
 * Review repository interface (Port)
 */
public interface ReviewRepository {
    Review save(Review review);
    Optional<Review> findById(ReviewId id);
    List<Review> findByUserId(String userId);
    List<Review> findByBookId(String bookId);
    Optional<Review> findByUserIdAndBookId(String userId, String bookId);
    List<Review> findAll();
    void delete(ReviewId id);
}
