package com.library.recommendation.domain.repository;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.recommendation.domain.entity.BookReview;
import com.library.recommendation.domain.valueobject.ReviewId;
import com.library.user.domain.valueobject.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Review repository interface (Port)
 */
public interface ReviewRepository {
    BookReview save(BookReview review);
    Optional<BookReview> findById(ReviewId id);
    List<BookReview> findByUserId(UserId userId);
    List<BookReview> findByPublicationId(PublicationId publicationId);
    Optional<BookReview> findByUserIdAndPublicationId(UserId userId, PublicationId publicationId);
    List<BookReview> findAll();
    void delete(ReviewId id);
}
