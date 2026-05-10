package com.library.recommendation.infrastructure.persistence.repository;

import com.library.recommendation.infrastructure.persistence.entity.UserInteractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInteractionJpaRepository extends JpaRepository<UserInteractionEntity, Long> {
}
