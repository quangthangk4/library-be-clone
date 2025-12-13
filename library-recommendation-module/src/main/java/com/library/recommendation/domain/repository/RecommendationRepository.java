package com.library.recommendation.domain.repository;

import com.library.recommendation.domain.model.Recommendation;
import com.library.recommendation.domain.model.RecommendationType;
import com.library.recommendation.domain.valueobject.RecommendationId;

import java.util.List;
import java.util.Optional;

/**
 * Recommendation repository interface (Port)
 */
public interface RecommendationRepository {
    Recommendation save(Recommendation recommendation);
    Optional<Recommendation> findById(RecommendationId id);
    List<Recommendation> findByUserId(String userId);
    List<Recommendation> findByUserIdAndType(String userId, RecommendationType type);
    List<Recommendation> findHighlyRelevant(String userId);
    List<Recommendation> findAll();
    void delete(RecommendationId id);
}
