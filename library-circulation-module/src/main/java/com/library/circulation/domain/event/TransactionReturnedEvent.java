package com.library.circulation.domain.event;

import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.domain.valueobject.TransactionId;
import lombok.Value;

/**
 * Domain event fired when a borrowed item is returned.
 */
@Value
public class TransactionReturnedEvent {
    TransactionId transactionId;
    ItemId itemId;
}
