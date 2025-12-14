package com.library.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "publication_authors")
@IdClass(PublicationAuthorId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationAuthorEntity {

    @Id
    @Column(name = "publication_id")
    private Long publicationId;

    @Id
    @Column(name = "author_id")
    private Long authorId;
}
