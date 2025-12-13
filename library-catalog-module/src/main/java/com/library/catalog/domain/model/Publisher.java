package com.library.catalog.domain.model;

import com.library.catalog.domain.valueobject.PublisherId;

import java.time.LocalDateTime;

/**
 * Publisher domain model
 */
public class Publisher {
    private final PublisherId id;
    private String name;
    private String address;
    private String website;
    private String contactEmail;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Publisher(PublisherId id,
                    String name,
                    String address,
                    String website,
                    String contactEmail,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.website = website;
        this.contactEmail = contactEmail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method
    public static Publisher create(String name, String address, String website, String contactEmail) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Publisher name cannot be empty");
        }

        PublisherId id = PublisherId.generate();
        LocalDateTime now = LocalDateTime.now();

        return new Publisher(id, name, address, website, contactEmail, now, now);
    }

    // Business logic: Update publisher information
    public void updateInfo(String name, String address, String website, String contactEmail) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        this.address = address;
        this.website = website;
        this.contactEmail = contactEmail;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public PublisherId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
