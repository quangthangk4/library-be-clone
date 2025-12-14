package com.library.catalog.application.mapper;

import com.library.catalog.application.dto.response.TagResponse;
import com.library.catalog.domain.entities.Tag;
import com.library.catalog.infrastructure.persistence.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(source = "id.value", target = "id")
    TagResponse toResponse(Tag tag);

    @Mapping(source = "id", target = "id")
    TagResponse entityToResponse(TagEntity entity);
}
