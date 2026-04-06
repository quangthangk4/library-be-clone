package com.library.catalog.domain.entities;

import com.library.catalog.domain.enums.ConditionItemEnum;
import com.library.catalog.domain.event.ItemStatusChangedEvent;
import com.library.catalog.domain.valueobject.Barcode;
import com.library.catalog.domain.valueobject.ItemId;
import com.library.catalog.domain.valueobject.PublicationId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Item aggregate root - represents a physical copy of a publication.
 * Separate from Publication to handle inventory management.
 * Only physical items (hardcover, paperback, journal) - no digital formats.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item {
    // Identity
    private final ItemId id;
    private final PublicationId publicationId;
    private final Barcode barcode;

    // Item properties
    private ItemStatus status;
    private final ItemType itemType;
    private String branch;
    private String shelf;
    private ConditionItemEnum condition;
    private final LocalDate acquiredDate;

    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    /**
     * Factory method to create a new Item.
     */
    public static Item create(
            PublicationId publicationId,
            Barcode barcode,
            ItemType itemType,
            LocalDate acquiredDate,
            String branch,
            String shelf
    ) {
        if (publicationId == null) {
            throw new IllegalArgumentException("Publication ID cannot be null");
        }
        if (barcode == null) {
            throw new IllegalArgumentException("Barcode cannot be null");
        }
        if (itemType == null) {
            throw new IllegalArgumentException("Item type cannot be null");
        }

        return new Item(
            ItemId.generate(),
            publicationId,
            barcode,
            ItemStatus.AVAILABLE,
            itemType,
            branch,
            shelf,
            ConditionItemEnum.NEW,
            acquiredDate != null ? acquiredDate : LocalDate.now()
        );
    }

    /**
     * Factory method for mapper (when loading from a database).
     */
    public static Item createForMapper(
            ItemId id,
            PublicationId publicationId,
            Barcode barcode,
            ItemStatus status,
            ItemType itemType,
            String branch,
            String shelf,
            ConditionItemEnum condition,
            LocalDate acquiredDate
    ) {
        return new Item(id, publicationId, barcode, status, itemType, branch, shelf, condition, acquiredDate);
    }

    // ========== Status Transition Methods ==========

    /**
     * Marks item as available.
     */
    public void markAsAvailable() {
        changeStatus(ItemStatus.AVAILABLE);
    }

    /**
     * Marks item as borrowed.
     */
    public void markAsBorrowed() {
        if (!isAvailable()) {
            throw new IllegalStateException(
                "Only available items can be borrowed. Current status: " + this.status
            );
        }
        changeStatus(ItemStatus.BORROWED);
    }

    /**
     * Marks item as reserved.
     */
    public void markAsReserved() {
        if (!isAvailable()) {
            throw new IllegalStateException(
                "Only available items can be reserved. Current status: " + this.status
            );
        }
        changeStatus(ItemStatus.RESERVED);
    }

    /**
     * Sends item to maintenance.
     */
    public void sendToMaintenance() {
        if (status == ItemStatus.BORROWED) {
            throw new IllegalStateException("Cannot send borrowed item to maintenance");
        }
        changeStatus(ItemStatus.IN_MAINTENANCE);
    }

    /**
     * Marks item as lost.
     */
    public void markAsLost() {
        changeStatus(ItemStatus.LOST);
    }

    /**
     * Returns item from borrowing (marks as available).
     */
    public void returnItem() {
        if (status != ItemStatus.BORROWED) {
            throw new IllegalStateException(
                "Only borrowed items can be returned. Current status: " + this.status
            );
        }
        changeStatus(ItemStatus.AVAILABLE);
    }

    /**
     * Completes maintenance and marks item as available.
     */
    public void completeMaintenance() {
        if (status != ItemStatus.IN_MAINTENANCE) {
            throw new IllegalStateException("Item is not under maintenance");
        }
        changeStatus(ItemStatus.AVAILABLE);
    }

    /**
     * Changes item status and fires domain event.
     */
    private void changeStatus(ItemStatus newStatus) {
        ItemStatus oldStatus = this.status;
        this.status = newStatus;

        // Fire domain event if status actually changed
        if (oldStatus != newStatus) {
            addDomainEvent(new ItemStatusChangedEvent(this.id, oldStatus, newStatus));
        }
    }

    // ========== Location Management ==========

    /**
     * Updates item location (branch and shelf).
     */
    public void updateLocation(String branch, String shelf) {
        this.branch = branch != null ? branch.trim() : null;
        this.shelf = shelf != null ? shelf.trim() : null;
    }

    // ========== Query Methods ==========

    /**
     * Checks if an item is available for borrowing.
     */
    public boolean isAvailable() {
        return status == ItemStatus.AVAILABLE;
    }

    /**
     * Checks if an item can be borrowed.
     */
    public boolean canBeBorrowed() {
        return status == ItemStatus.AVAILABLE;
    }

    /**
     * Checks if an item is currently borrowed.
     */
    public boolean isBorrowed() {
        return status == ItemStatus.BORROWED;
    }

    /**
     * Checks if an item is reserved.
     */
    public boolean isReserved() {
        return status == ItemStatus.RESERVED;
    }

    /**
     * Checks if an item is under maintenance.
     */
    public boolean isInMaintenance() {
        return status == ItemStatus.IN_MAINTENANCE;
    }

    /**
     * Checks if an item is lost.
     */
    public boolean isLost() {
        return status == ItemStatus.LOST;
    }

    // ========== Domain Events Management ==========

    /**
     * Polls and clears all domain events.
     */
    public List<Object> pollDomainEvents() {
        List<Object> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }
}
