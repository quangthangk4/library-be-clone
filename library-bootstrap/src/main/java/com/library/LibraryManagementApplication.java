package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Library Management System.
 *
 * This is a Modular Monolith application following Clean Architecture and DDD principles.
 *
 * Modules:
 * - library-user-module: User, Role, Permission management
 * - library-catalog-module: Publication, Item, Author, Publisher, Category, Tag management
 * - library-circulation-module: BorrowingTransaction, Reservation, Fine management
 * - library-recommendation-module: AI-powered recommendations, SearchHistory, UserInteraction, Rating, Review
 * - library-shared: Common utilities and shared components
 */
@SpringBootApplication(scanBasePackages = "com.library")
public class LibraryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }
}
