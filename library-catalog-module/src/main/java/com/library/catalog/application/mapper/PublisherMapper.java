package com.library.catalog.application.mapper;

import com.library.catalog.application.dto.response.PublisherResponse;
import com.library.catalog.domain.entities.Publisher;
import com.library.catalog.infrastructure.persistence.entity.PublisherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    @Mapping(source = "id.value", target = "id")
    PublisherResponse toResponse(Publisher publisher);

    @Mapping(source = "id", target = "id")
    PublisherResponse entityToResponse(PublisherEntity entity);
}
