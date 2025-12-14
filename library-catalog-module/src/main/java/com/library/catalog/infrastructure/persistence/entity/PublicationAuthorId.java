package com.library.catalog.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationAuthorId implements Serializable {
    private Long publicationId;
    private Long authorId;
}
