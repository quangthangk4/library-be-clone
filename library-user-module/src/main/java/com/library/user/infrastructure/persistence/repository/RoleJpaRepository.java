package com.library.user.infrastructure.persistence.repository;

import com.library.user.infrastructure.persistence.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository for RoleJpaEntity
 */
@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, Long> {

    /**
     * Find role by name
     */
    Optional<RoleJpaEntity> findByRoleName(String roleName);

    /**
     * Check if role exists by name
     */
    boolean existsByRoleName(String roleName);
}
