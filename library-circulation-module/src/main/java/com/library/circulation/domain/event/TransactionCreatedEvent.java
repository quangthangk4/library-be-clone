package com.library.circulation.domain.event;

import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.user.domain.valueobject.UserId;
import lombok.Value;

/**
 * Domain event fired when a borrowing transaction is created.
 */
@Value
public class TransactionCreatedEvent {
    TransactionId transactionId;
    UserId userId;
    ItemId itemId;
}
