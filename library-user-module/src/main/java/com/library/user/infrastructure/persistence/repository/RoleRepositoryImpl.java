package com.library.user.infrastructure.persistence.repository;

import com.library.user.domain.model.RoleAggregate;
import com.library.user.domain.repository.RoleRepository;
import com.library.user.domain.valueobject.RoleId;
import com.library.user.infrastructure.mapper.UserEntityMapper;
import com.library.user.infrastructure.persistence.entity.RoleJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementation of RoleRepository
 * Adapts Spring Data JPA repository to Domain repository interface
 */
@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;
    private final UserEntityMapper entityMapper;

    @Override
    public RoleAggregate save(RoleAggregate role) {
        RoleJpaEntity entity = entityMapper.toJpaEntity(role);
        RoleJpaEntity savedEntity = roleJpaRepository.save(entity);
        return entityMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<RoleAggregate> findById(RoleId roleId) {
        return roleJpaRepository.findById(roleId.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<RoleAggregate> findByName(String roleName) {
        return roleJpaRepository.findByRoleName(roleName)
            .map(entityMapper::toDomainModel);
    }

    @Override
    public boolean existsByName(String roleName) {
        return roleJpaRepository.existsByRoleName(roleName);
    }

    @Override
    public void delete(RoleAggregate role) {
        roleJpaRepository.deleteById(role.getId().getValue());
    }

    @Override
    public void deleteById(RoleId roleId) {
        roleJpaRepository.deleteById(roleId.getValue());
    }
}
