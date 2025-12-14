package com.library.circulation.domain.event;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.domain.valueobject.ReservationId;
import com.library.user.domain.valueobject.UserId;
import lombok.Value;

/**
 * Domain event fired when a reservation is created.
 */
@Value
public class ReservationCreatedEvent {
    ReservationId reservationId;
    UserId userId;
    PublicationId publicationId;
}
