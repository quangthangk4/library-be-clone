package com.library.circulation.domain.service;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.valueobject.ItemId;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.entities.Fine;
import com.library.circulation.domain.entities.PaymentStatus;
import com.library.circulation.domain.entities.TransactionStatus;
import com.library.circulation.domain.repository.BorrowingTransactionRepository;
import com.library.circulation.domain.repository.FineRepository;
import com.library.circulation.domain.repository.ReservationRepository;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.user.domain.entities.User;
import com.library.user.domain.entities.UserStatus;
import com.library.user.domain.repository.UserRepository;
import com.library.user.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of CirculationDomainService.
 */
@RequiredArgsConstructor
public class CirculationDomainServiceImpl implements CirculationDomainService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BorrowingTransactionRepository transactionRepository;
    private final ReservationRepository reservationRepository;
    private final FineRepository fineRepository;

    // Business constants
    private static final int MAX_BORROW_LIMIT = 5;
    private static final int BORROW_PERIOD_DAYS = 14;
    private static final BigDecimal FINE_RATE_PER_DAY = new BigDecimal("1000");

    @Override
    public void validateUserCanBorrow(UserId userId) {
        // Check if user exists
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Check account status - must be ACTIVE
        if (user.getStatus() != UserStatus.ACTIVE) {
            if (user.getStatus() == UserStatus.SUSPENDED) {
                throw new AppException(ErrorCode.ACCOUNT_SUSPENDED);
            }
            throw new AppException(ErrorCode.USER_NOT_ACTIVE);
        }

        // Check for unpaid fines
        List<Fine> unpaidFines = fineRepository.findUnpaidByUserId(userId);
        if (!unpaidFines.isEmpty()) {
            throw new AppException(ErrorCode.USER_HAS_UNPAID_FINES);
        }
    }

    @Override
    public void validateItemAvailableForBorrow(ItemId itemId) {
        // Check if item exists
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));

        // Check if item is available
        if (!item.isAvailable()) {
            throw new AppException(ErrorCode.ITEM_NOT_AVAILABLE);
        }
    }

    @Override
    public void validateReservation(UserId userId, PublicationId publicationId) {
        // Check if user already has a pending reservation for this publication
        boolean exists = reservationRepository.existsPendingByUserAndPublication(userId, publicationId);
        if (exists) {
            throw new AppException(ErrorCode.RESERVATION_ALREADY_EXISTS);
        }
    }

    @Override
    public LocalDate calculateDueDate(LocalDateTime borrowedDate) {
        if (borrowedDate == null) {
            throw new IllegalArgumentException("Borrowed date cannot be null");
        }
        return borrowedDate.toLocalDate().plusDays(BORROW_PERIOD_DAYS);
    }

    @Override
    public BigDecimal calculateFineAmount(int daysOverdue) {
        if (daysOverdue < 0) {
            throw new IllegalArgumentException("Days overdue cannot be negative");
        }
        return FINE_RATE_PER_DAY.multiply(new BigDecimal(daysOverdue));
    }

    @Override
    public void checkUserBorrowLimit(UserId userId) {
        // Get active transactions for user
        List<BorrowingTransaction> activeTransactions = transactionRepository.findActiveByUserId(userId);

        if (activeTransactions.size() >= MAX_BORROW_LIMIT) {
            throw new AppException(ErrorCode.USER_BORROW_LIMIT_EXCEEDED);
        }
    }
}
