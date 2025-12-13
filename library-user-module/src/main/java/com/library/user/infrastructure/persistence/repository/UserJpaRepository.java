package com.library.user.infrastructure.persistence.repository;

import com.library.user.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UserJpaEntity
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    /**
     * Find user by username
     */
    Optional<UserJpaEntity> findByUsername(String username);

    /**
     * Find user by email
     */
    Optional<UserJpaEntity> findByEmail(String email);

    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Find users by role name
     */
    @Query("SELECT u FROM UserJpaEntity u JOIN u.roles r WHERE r.roleName = :roleName")
    List<UserJpaEntity> findByRoleName(@Param("roleName") String roleName);
}
