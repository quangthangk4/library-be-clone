package com.library.user.infrastructure.persistence.repository;

import com.library.user.domain.model.Permission;
import com.library.user.domain.repository.PermissionRepository;
import com.library.user.domain.valueobject.PermissionId;
import com.library.user.infrastructure.mapper.UserEntityMapper;
import com.library.user.infrastructure.persistence.entity.PermissionJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of PermissionRepository
 * Adapts Spring Data JPA repository to Domain repository interface
 */
@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionJpaRepository permissionJpaRepository;
    private final UserEntityMapper entityMapper;

    @Override
    public Permission save(Permission permission) {
        PermissionJpaEntity entity = entityMapper.toJpaEntity(permission);
        PermissionJpaEntity savedEntity = permissionJpaRepository.save(entity);
        return entityMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<Permission> findById(PermissionId permissionId) {
        return permissionJpaRepository.findById(permissionId.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<Permission> findByName(String permissionName) {
        return permissionJpaRepository.findByPermissionName(permissionName)
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Set<Permission> findByNames(Set<String> permissionNames) {
        return permissionJpaRepository.findByPermissionNameIn(permissionNames).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toSet());
    }

    @Override
    public List<Permission> findAll() {
        return permissionJpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String permissionName) {
        return permissionJpaRepository.existsByPermissionName(permissionName);
    }

    @Override
    public void delete(Permission permission) {
        permissionJpaRepository.deleteById(permission.getId().getValue());
    }

    @Override
    public void deleteById(PermissionId permissionId) {
        permissionJpaRepository.deleteById(permissionId.getValue());
    }
}
