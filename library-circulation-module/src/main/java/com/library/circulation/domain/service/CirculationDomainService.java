package com.library.circulation.domain.service;

import com.library.catalog.domain.valueobject.ItemId;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.user.domain.valueobject.UserId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Domain Service for Circulation business logic.
 * Contains business rules that don't naturally fit within a single aggregate.
 */
public interface CirculationDomainService {

    /**
     * Validates if user can borrow books.
     * Checks account status, existing transactions, and unpaid fines.
     *
     * @param userId the user ID to validate
     * @throws com.library.shared.exception.AppException if user cannot borrow
     */
    void validateUserCanBorrow(UserId userId);

    /**
     * Validates if item is available for borrowing.
     * Checks item status (must be AVAILABLE).
     *
     * @param itemId the item ID to validate
     * @throws com.library.shared.exception.AppException if item is not available
     */
    void validateItemAvailableForBorrow(ItemId itemId);

    /**
     * Validates if user can create a reservation.
     * Checks if user already has a pending reservation for the same publication.
     *
     * @param userId the user ID
     * @param publicationId the publication ID
     * @throws com.library.shared.exception.AppException if validation fails
     */
    void validateReservation(UserId userId, PublicationId publicationId);

    /**
     * Calculates due date for a borrowing transaction.
     * Default: 14 days from borrowed date.
     *
     * @param borrowedDate the borrowed date
     * @return the calculated due date
     */
    LocalDate calculateDueDate(LocalDateTime borrowedDate);

    /**
     * Calculates fine amount based on days overdue.
     * Rate: 1000 VND per day.
     *
     * @param daysOverdue number of days overdue
     * @return the calculated fine amount
     */
    BigDecimal calculateFineAmount(int daysOverdue);

    /**
     * Checks if user has reached the borrow limit.
     * Maximum: 5 active borrowing transactions.
     *
     * @param userId the user ID
     * @throws com.library.shared.exception.AppException if limit exceeded
     */
    void checkUserBorrowLimit(UserId userId);
}
