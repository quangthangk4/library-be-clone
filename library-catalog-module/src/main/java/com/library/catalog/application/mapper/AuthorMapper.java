package com.library.catalog.application.mapper;

import com.library.catalog.application.dto.response.AuthorResponse;
import com.library.catalog.domain.entities.Author;
import com.library.catalog.infrastructure.persistence.entity.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(source = "id.value", target = "id")
    AuthorResponse toResponse(Author author);

    @Mapping(source = "id", target = "id")
    AuthorResponse entityToResponse(AuthorEntity entity);
}
