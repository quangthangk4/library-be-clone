package com.library.circulation.infrastructure.persistence.entity;

import com.library.circulation.domain.entities.PaymentStatus;
import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * JPA Entity for Fine table.
 */
@Entity
@Table(name = "fines", indexes = {
    @Index(name = "idx_transaction_id", columnList = "transaction_id"),
    @Index(name = "idx_payment_status", columnList = "payment_status"),
    @Index(name = "idx_fine_date", columnList = "fine_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FineEntity extends BaseEntity {

    @Column(name = "transaction_id", nullable = false, unique = true)
    private Long transactionId;

    @Column(name = "fine_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal fineAmount;

    @Column(name = "fine_date", nullable = false)
    private LocalDate fineDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Column(name = "paid_date")
    private LocalDateTime paidDate;
}
