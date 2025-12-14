package com.library.catalog.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "publication_tags")
@IdClass(PublicationTagId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationTagEntity {

    @Id
    @Column(name = "publication_id")
    private Long publicationId;

    @Id
    @Column(name = "tag_id")
    private Long tagId;
}
