package com.library.recommendation.domain.repository;

import com.library.recommendation.domain.model.UserPreference;
import com.library.recommendation.domain.valueobject.UserPreferenceId;

import java.util.List;
import java.util.Optional;

/**
 * UserPreference repository interface (Port)
 */
public interface UserPreferenceRepository {
    UserPreference save(UserPreference userPreference);
    Optional<UserPreference> findById(UserPreferenceId id);
    Optional<UserPreference> findByUserId(String userId);
    List<UserPreference> findAll();
    void delete(UserPreferenceId id);
}
