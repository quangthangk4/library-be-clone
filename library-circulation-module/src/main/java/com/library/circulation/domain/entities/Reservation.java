package com.library.circulation.domain.entities;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.domain.enums.ReservationStatus;
import com.library.circulation.domain.valueobject.ReservationId;
import com.library.user.domain.valueobject.UserId;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Reservation {

  // Identity
  private ReservationId id;
  private UserId userId;
  private PublicationId publicationId;

  // Reservation metadata
  private Instant reservationDate;
  private ReservationStatus status;
  private int queuePosition;
  private Instant holdExpirationTime;
  // Domain events
  private final List<Object> domainEvents = new ArrayList<>();


  private void addDomainEvent(Object event) {
    this.domainEvents.add(event);
  }

  public List<Object> pollDomainEvents() {
    List<Object> events = new ArrayList<>(this.domainEvents);
    this.domainEvents.clear();
    return events;
  }
}
