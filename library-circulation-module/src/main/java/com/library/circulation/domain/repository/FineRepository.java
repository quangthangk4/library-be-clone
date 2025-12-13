package com.library.circulation.domain.repository;

import com.library.circulation.domain.model.Fine;
import com.library.circulation.domain.valueobject.FineId;

import java.util.List;
import java.util.Optional;

/**
 * Fine repository interface (Port)
 */
public interface FineRepository {
    Fine save(Fine fine);
    Optional<Fine> findById(FineId id);
    List<Fine> findByUserId(String userId);
    List<Fine> findByLoanId(String loanId);
    List<Fine> findUnpaidFines();
    List<Fine> findAll();
    void delete(FineId id);
}
