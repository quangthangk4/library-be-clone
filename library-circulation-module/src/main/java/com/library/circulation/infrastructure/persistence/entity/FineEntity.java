package com.library.circulation.infrastructure.persistence.entity;

import com.library.circulation.domain.entities.PaymentStatus;
import com.library.shared.entity.BaseEntity;
import com.library.user.domain.enums.ViolationType;
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
    @Index(name = "idx_fine_transaction_id", columnList = "transactionId"),
    @Index(name = "idx_fine_payment_status", columnList = "paymentStatus"),
    @Index(name = "idx_fine_date", columnList = "fineDate")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FineEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Long transactionId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fineAmount;

    @Column(nullable = false)
    private LocalDate fineDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    private LocalDateTime paidDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ViolationType violationType;
}
