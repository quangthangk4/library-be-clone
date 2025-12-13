package com.library.user.infrastructure.persistence.repository;

import com.library.user.infrastructure.persistence.entity.PermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Spring Data JPA Repository for PermissionJpaEntity
 */
@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, Long> {

    /**
     * Find permission by name
     */
    Optional<PermissionJpaEntity> findByPermissionName(String permissionName);

    /**
     * Find permissions by names
     */
    Set<PermissionJpaEntity> findByPermissionNameIn(Set<String> permissionNames);

    /**
     * Check if permission exists by name
     */
    boolean existsByPermissionName(String permissionName);
}
