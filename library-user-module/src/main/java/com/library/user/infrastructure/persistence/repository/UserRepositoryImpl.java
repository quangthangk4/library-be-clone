package com.library.user.infrastructure.persistence.repository;

import com.library.user.domain.model.UserAggregate;
import com.library.user.domain.repository.UserRepositoryInterface;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.UserId;
import com.library.user.infrastructure.mapper.UserEntityMapper;
import com.library.user.infrastructure.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of UserRepositoryInterface
 * Adapts Spring Data JPA repository to Domain repository interface
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryInterface {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper entityMapper;

    @Override
    public UserAggregate save(UserAggregate user) {
        UserJpaEntity entity = entityMapper.toJpaEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(entity);
        return entityMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<UserAggregate> findById(UserId userId) {
        return userJpaRepository.findById(userId.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<UserAggregate> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<UserAggregate> findByEmail(Email email) {
        return userJpaRepository.findByEmail(email.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<UserAggregate> findAll() {
        return userJpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserAggregate> findByRoleName(String roleName) {
        return userJpaRepository.findByRoleName(roleName).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return userJpaRepository.existsByEmail(email.getValue());
    }

    @Override
    public void delete(UserAggregate user) {
        userJpaRepository.deleteById(user.getId().getValue());
    }

    @Override
    public void deleteById(UserId userId) {
        userJpaRepository.deleteById(userId.getValue());
    }

    @Override
    public long count() {
        return userJpaRepository.count();
    }
}
