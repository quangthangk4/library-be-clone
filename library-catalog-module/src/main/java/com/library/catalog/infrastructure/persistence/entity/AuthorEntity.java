package com.library.catalog.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "authors", indexes = {
    @Index(name = "idx_author_name", columnList = "author_name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity extends BaseEntity {

    @Column(name = "author_name", nullable = false, length = 100)
    private String authorName;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_death")
    private LocalDate dateOfDeath;
}
