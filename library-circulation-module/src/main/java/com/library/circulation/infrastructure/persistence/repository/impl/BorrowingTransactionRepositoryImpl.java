package com.library.circulation.infrastructure.persistence.repository.impl;

import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.entities.TransactionStatus;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.circulation.infrastructure.persistence.entity.BorrowingTransactionEntity;
import com.library.circulation.infrastructure.persistence.mapper.BorrowingTransactionEntityMapper;
import com.library.circulation.infrastructure.persistence.repository.BorrowingTransactionJpaRepository;
import com.library.user.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of BorrowingTransactionRepository.
 */
@Repository
@RequiredArgsConstructor
public class BorrowingTransactionRepositoryImpl implements BorrowingTransactionRepository {

    private final BorrowingTransactionJpaRepository jpaRepository;
    private final BorrowingTransactionEntityMapper entityMapper;

    @Override
    public BorrowingTransaction save(BorrowingTransaction transaction) {
        BorrowingTransactionEntity entity = entityMapper.toEntity(transaction);
        BorrowingTransactionEntity savedEntity = jpaRepository.save(entity);
        return entityMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<BorrowingTransaction> findById(TransactionId transactionId) {
        return jpaRepository.findById(transactionId.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<BorrowingTransaction> findAll() {
        return jpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(BorrowingTransaction transaction) {
        jpaRepository.deleteById(transaction.getId().getValue());
    }

    @Override
    public void deleteById(TransactionId transactionId) {
        jpaRepository.deleteById(transactionId.getValue());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public List<BorrowingTransaction> findByUserId(UserId userId) {
        return jpaRepository.findByUserId(userId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingTransaction> findByItemId(ItemId itemId) {
        return jpaRepository.findByItemId(itemId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingTransaction> findActiveByUserId(UserId userId) {
        return jpaRepository.findActiveByUserId(userId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingTransaction> findOverdueTransactions() {
        return jpaRepository.findOverdueTransactions(LocalDate.now()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsActiveByItemId(ItemId itemId) {
        return jpaRepository.existsByItemIdAndStatus(itemId.getValue(), TransactionStatus.ACTIVE);
    }
}
