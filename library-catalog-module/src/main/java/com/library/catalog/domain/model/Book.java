package com.library.catalog.domain.model;

import com.library.catalog.domain.valueobject.BookId;
import com.library.catalog.domain.valueobject.ISBN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Book domain model - Pure Java object with business logic
 */
public class Book {
    private final BookId id;
    private String title;
    private ISBN isbn;
    private String description;
    private LocalDate publishedDate;
    private int totalCopies;
    private int availableCopies;
    private String language;
    private Integer pageCount;
    private BookStatus status;

    // Relationships (using IDs to avoid circular dependencies)
    private String publisherId;
    private String categoryId;
    private List<String> authorIds;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Book(BookId id,
                String title,
                ISBN isbn,
                String description,
                LocalDate publishedDate,
                int totalCopies,
                int availableCopies,
                String language,
                Integer pageCount,
                BookStatus status,
                String publisherId,
                String categoryId,
                List<String> authorIds,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.publishedDate = publishedDate;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.language = language;
        this.pageCount = pageCount;
        this.status = status;
        this.publisherId = publisherId;
        this.categoryId = categoryId;
        this.authorIds = authorIds != null ? new ArrayList<>(authorIds) : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method
    public static Book create(String title, ISBN isbn, String description, LocalDate publishedDate,
                             int totalCopies, String language, Integer pageCount,
                             String publisherId, String categoryId, List<String> authorIds) {
        if (totalCopies < 0) {
            throw new IllegalArgumentException("Total copies cannot be negative");
        }

        BookId id = BookId.generate();
        LocalDateTime now = LocalDateTime.now();

        return new Book(id, title, isbn, description, publishedDate, totalCopies, totalCopies,
                       language, pageCount, BookStatus.AVAILABLE, publisherId, categoryId,
                       authorIds, now, now);
    }

    // Business logic: Check if book is available for borrowing
    public boolean isAvailable() {
        return this.status == BookStatus.AVAILABLE && this.availableCopies > 0;
    }

    // Business logic: Check if book can be reserved
    public boolean canBeReserved() {
        return this.availableCopies == 0 && (this.status == BookStatus.AVAILABLE || this.status == BookStatus.BORROWED);
    }

    // Business logic: Borrow book
    public void borrow() {
        if (!isAvailable()) {
            throw new IllegalStateException("Book is not available for borrowing");
        }
        this.availableCopies--;
        if (this.availableCopies == 0) {
            this.status = BookStatus.BORROWED;
        }
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Return book
    public void returnBook() {
        if (this.availableCopies >= this.totalCopies) {
            throw new IllegalStateException("Cannot return more books than total copies");
        }
        this.availableCopies++;
        if (this.availableCopies > 0 && this.status == BookStatus.BORROWED) {
            this.status = BookStatus.AVAILABLE;
        }
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Mark as lost
    public void markAsLost() {
        if (this.totalCopies > 0) {
            this.totalCopies--;
        }
        this.status = BookStatus.LOST;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Mark as damaged
    public void markAsDamaged() {
        if (this.availableCopies > 0) {
            this.availableCopies--;
        }
        this.status = BookStatus.DAMAGED;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Repair damaged book
    public void repair() {
        if (this.status != BookStatus.DAMAGED) {
            throw new IllegalStateException("Only damaged books can be repaired");
        }
        this.availableCopies++;
        this.status = BookStatus.AVAILABLE;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Update book information
    public void updateInfo(String title, String description, LocalDate publishedDate,
                          String language, Integer pageCount) {
        if (title != null && !title.trim().isEmpty()) {
            this.title = title;
        }
        this.description = description;
        this.publishedDate = publishedDate;
        this.language = language;
        this.pageCount = pageCount;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Add copies
    public void addCopies(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }
        this.totalCopies += count;
        this.availableCopies += count;
        if (this.availableCopies > 0 && this.status != BookStatus.AVAILABLE) {
            this.status = BookStatus.AVAILABLE;
        }
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public BookId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public List<String> getAuthorIds() {
        return new ArrayList<>(authorIds);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
