package com.library.catalog.application.mapper;

import com.library.catalog.application.dto.response.ItemResponse;
import com.library.catalog.domain.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "publicationId.value", target = "publicationId")
    @Mapping(source = "barcode.value", target = "barcode")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "itemType", target = "itemType")
    @Mapping(target = "publicationTitle", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ItemResponse toResponse(Item item);
}
