package com.library.circulation.domain.entities;

import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.domain.enums.TransactionStatus;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.user.domain.valueobject.UserId;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class BorrowingTransaction {

  private final TransactionId id;
  private final UserId userId;
  private final ItemId itemId;

  private UserId librarianIdIssue;
  private UserId librarianIdReturn;

  private final Instant borrowedDate;
  private final Instant dueDate;
  private Instant returnedDate;
  private Instant pickedUpDeadline;

  private TransactionStatus status;
  private int renewalCount;

  private final List<Object> domainEvents = new ArrayList<>();

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
