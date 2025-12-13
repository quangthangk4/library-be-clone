package com.library.circulation.domain.repository;

import com.library.circulation.domain.model.Loan;
import com.library.circulation.domain.valueobject.LoanId;

import java.util.List;
import java.util.Optional;

/**
 * Loan repository interface (Port)
 */
public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(LoanId id);
    List<Loan> findByUserId(String userId);
    List<Loan> findByBookId(String bookId);
    List<Loan> findActiveLoans();
    List<Loan> findOverdueLoans();
    List<Loan> findAll();
    void delete(LoanId id);
}
