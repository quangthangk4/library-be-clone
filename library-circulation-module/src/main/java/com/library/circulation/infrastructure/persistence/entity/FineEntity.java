package com.library.circulation.infrastructure.persistence.entity;

import com.library.circulation.domain.enums.PaymentStatus;
import com.library.shared.entity.BaseEntity;
import com.library.user.domain.enums.ViolationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * JPA Entity for Fine table.
 */
@Entity
@Table(name = "fines", indexes = {
    @Index(name = "idx_fine_transaction_id", columnList = "transactionId")
})
@Getter
@Setter
@AllArgsConstructor
@Builder
public class FineEntity extends BaseEntity {

    @Column(name = "transaction_id", nullable = false, unique = true)
    private Long transactionId;

    @Column(nullable = false, precision = 15, scale = 0)
    private BigDecimal fineAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    private Instant paidDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ViolationType type;

    protected FineEntity() { }
}
