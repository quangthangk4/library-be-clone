package com.library.catalog.infrastructure.persistence.mapper;

import com.library.catalog.domain.entities.Author;
import com.library.catalog.domain.valueobject.AuthorId;
import com.library.catalog.infrastructure.persistence.entity.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorEntityMapper {

    public AuthorEntity toEntity(Author author) {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(author.getId().getValue());
        entity.setAuthorName(author.getAuthorName());
        entity.setBiography(author.getBiography());
        entity.setDateOfBirth(author.getDateOfBirth());
        entity.setDateOfDeath(author.getDateOfDeath());
        return entity;
    }

    public Author toDomainModel(AuthorEntity entity) {
        return Author.of(
            AuthorId.of(entity.getId()),
            entity.getAuthorName(),
            entity.getBiography(),
            entity.getDateOfBirth(),
            entity.getDateOfDeath()
        );
    }
}
