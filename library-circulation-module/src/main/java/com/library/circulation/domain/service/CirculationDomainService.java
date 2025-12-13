package com.library.circulation.domain.service;

import com.library.circulation.domain.model.Fine;
import com.library.circulation.domain.model.Loan;
import com.library.circulation.domain.repository.LoanRepository;
import com.library.circulation.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Circulation domain service
 * Contains domain logic for loan, reservation, and fine operations
 */
public class CirculationDomainService {
    private final LoanRepository loanRepository;
    private final BigDecimal fineRatePerDay;
    private final Currency currency;

    public CirculationDomainService(LoanRepository loanRepository,
                                   BigDecimal fineRatePerDay,
                                   Currency currency) {
        this.loanRepository = loanRepository;
        this.fineRatePerDay = fineRatePerDay;
        this.currency = currency;
    }

    /**
     * Calculate fine for overdue loan
     */
    public Fine calculateOverdueFine(Loan loan) {
        if (!loan.isOverdue()) {
            throw new IllegalArgumentException("Loan is not overdue");
        }

        long overdueDays = loan.getOverdueDays();
        BigDecimal fineAmount = fineRatePerDay.multiply(BigDecimal.valueOf(overdueDays));
        Money amount = Money.of(fineAmount, currency);

        String reason = String.format("Overdue fine for %d days", overdueDays);
        return Fine.create(loan.getUserId(), loan.getId().getValue(), amount, reason);
    }

    /**
     * Check if user can borrow more books
     */
    public boolean canUserBorrowBooks(String userId, int maxActiveLoans) {
        var activeLoans = loanRepository.findByUserId(userId).stream()
            .filter(Loan::isActive)
            .count();
        return activeLoans < maxActiveLoans;
    }

    /**
     * Validate user can create a new loan
     */
    public void validateCanCreateLoan(String userId, int maxActiveLoans) {
        if (!canUserBorrowBooks(userId, maxActiveLoans)) {
            throw new IllegalStateException("User has reached maximum active loans limit");
        }
    }
}
