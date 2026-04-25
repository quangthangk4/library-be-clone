package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

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
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.library")
@ConfigurationPropertiesScan
public class LibraryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }
}
