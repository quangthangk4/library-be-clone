package com.library.circulation.domain.event;

import com.library.circulation.domain.valueobject.FineId;
import com.library.circulation.domain.valueobject.TransactionId;
import lombok.Value;

import java.math.BigDecimal;

/**
 * Domain event fired when a fine is created.
 */
@Value
public class FineCreatedEvent {
    FineId fineId;
    TransactionId transactionId;
    BigDecimal fineAmount;
}
