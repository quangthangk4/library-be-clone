package com.library.circulation.domain.entities;

import com.library.circulation.domain.enums.PaymentStatus;
import com.library.circulation.domain.event.FineCreatedEvent;
import com.library.circulation.domain.valueobject.FineId;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.shared.entity.BaseDomainEntity;
import com.library.user.domain.enums.ViolationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Fine extends BaseDomainEntity {
    // Identity
    private FineId id;
    private TransactionId transactionId;

    // Fine details
    private BigDecimal fineAmount;
    private Instant createAt;
    private PaymentStatus status;
    private Instant paidDate;
    private ViolationType type;

    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    public static Fine create(TransactionId transactionId, BigDecimal fineAmount, ViolationType violationType) {
        Fine fine = new Fine(
            FineId.generate(),
            transactionId,
            fineAmount,
            Instant.now(),
            PaymentStatus.UNPAID,
            null,
            violationType
        );
        fine.addDomainEvent(new FineCreatedEvent(fine.getId(), transactionId, fineAmount));
        return fine;
    }

    public static Fine of(FineId fineId, TransactionId transactionId, BigDecimal fineAmount, Instant createAt, PaymentStatus status, Instant paidDate, ViolationType violationType) {
        return new Fine(fineId, transactionId, fineAmount, createAt, status, paidDate, violationType);
    }

    public void markAsPaid() {
        if (this.status == PaymentStatus.PAID) {
            throw new IllegalStateException("Fine is already paid");
        }

        this.status = PaymentStatus.PAID;
        this.paidDate = Instant.now();
    }

    public boolean isOverdue() {
        if (this.status == PaymentStatus.PAID) {
            return false;
        }

        long daysSinceFine = ChronoUnit.DAYS.between(this.createAt, LocalDate.now());
        return daysSinceFine > 30;
    }

    // ============== Domain Events Management ==============

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }

    public List<Object> pollDomainEvents() {
        List<Object> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }
}
