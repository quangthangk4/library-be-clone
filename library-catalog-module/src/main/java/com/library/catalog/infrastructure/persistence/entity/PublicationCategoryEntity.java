package com.library.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "publication_categories")
@IdClass(PublicationCategoryId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationCategoryEntity {

    @Id
    @Column(name = "publication_id")
    private Long publicationId;

    @Id
    @Column(name = "category_id")
    private Long categoryId;
}
