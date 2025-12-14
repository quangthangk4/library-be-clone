package com.library.circulation.infrastructure.persistence.repository.impl;

import com.library.circulation.domain.entities.Fine;
import com.library.circulation.domain.repository.FineRepository;
import com.library.circulation.domain.valueobject.FineId;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.circulation.infrastructure.persistence.mapper.FineEntityMapper;
import com.library.circulation.infrastructure.persistence.repository.FineJpaRepository;
import com.library.user.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of FineRepository.
 */
@Repository
@RequiredArgsConstructor
public class FineRepositoryImpl implements FineRepository {

    private final FineJpaRepository jpaRepository;
    private final FineEntityMapper entityMapper;

    @Override
    public Fine save(Fine fine) {
        var entity = entityMapper.toEntity(fine);
        var savedEntity = jpaRepository.save(entity);
        return entityMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<Fine> findById(FineId fineId) {
        return jpaRepository.findById(fineId.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Fine> findAll() {
        return jpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Fine fine) {
        jpaRepository.deleteById(fine.getId().getValue());
    }

    @Override
    public void deleteById(FineId fineId) {
        jpaRepository.deleteById(fineId.getValue());
    }

    @Override
    public Optional<Fine> findByTransactionId(TransactionId transactionId) {
        return jpaRepository.findByTransactionId(transactionId.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Fine> findByUserId(UserId userId) {
        return jpaRepository.findByUserId(userId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Fine> findUnpaidByUserId(UserId userId) {
        return jpaRepository.findUnpaidByUserId(userId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalUnpaidAmountByUserId(UserId userId) {
        return jpaRepository.getTotalUnpaidAmountByUserId(userId.getValue());
    }
}
