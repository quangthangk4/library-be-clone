package com.library.catalog.domain.entities;

import com.library.catalog.domain.valueobject.PublisherId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Publisher entity - represents a book publisher/publishing house.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Publisher {
    private final PublisherId id;
    private String publisherName;
    private String address;

    /**
     * Factory method to create a new Publisher.
     */
    public static Publisher create(String publisherName, String address) {
        validatePublisherName(publisherName);
        return new Publisher(
            PublisherId.generate(),
            publisherName.trim(),
            address != null ? address.trim() : null
        );
    }

    /**
     * Factory method for mapper (when loading from database).
     */
    public static Publisher of(PublisherId id, String publisherName, String address) {
        validatePublisherName(publisherName);
        return new Publisher(id, publisherName.trim(), address);
    }

    /**
     * Updates publisher information.
     */
    public void updateInfo(String newName, String newAddress) {
        if (newName != null && !newName.isBlank()) {
            validatePublisherName(newName);
            this.publisherName = newName.trim();
        }
        if (newAddress != null) {
            this.address = newAddress.trim();
        }
    }

    /**
     * Updates address only.
     */
    public void updateAddress(String newAddress) {
        this.address = newAddress != null ? newAddress.trim() : null;
    }

    private static void validatePublisherName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Publisher name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Publisher name must not exceed 100 characters");
        }
    }
}
