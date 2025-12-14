package com.library.catalog.application.mapper;

import com.library.catalog.application.dto.request.CreatePublicationRequest;
import com.library.catalog.application.dto.response.PublicationResponse;
import com.library.catalog.domain.valueobject.PublicationMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublicationMapper {

    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "subtitle", source = "request.subtitle")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "language", source = "request.language")
    @Mapping(target = "numberOfPages", source = "request.numberOfPages")
    PublicationMetadata toMetadata(CreatePublicationRequest request);

    // Note: PublicationResponse requires manual construction in use case
    // because it needs enriched data from multiple sources (authors, publisher, categories, tags, item counts)
}
