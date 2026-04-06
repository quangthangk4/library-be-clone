package com.library.catalog.infrastructure.persistence.mapper;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.valueobject.Barcode;
import com.library.catalog.domain.valueobject.ItemId;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.catalog.infrastructure.persistence.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public class ItemEntityMapper {

    public ItemEntity toEntity(Item item) {
        ItemEntity entity = new ItemEntity();
        entity.setId(item.getId().getValue());
        entity.setPublicationId(item.getPublicationId().getValue());
        entity.setBarcode(item.getBarcode().getValue());
        entity.setStatus(item.getStatus());
        entity.setItemType(item.getItemType());
        entity.setBranch(item.getBranch());
        entity.setShelf(item.getShelf());
        entity.setCondition(item.getCondition());
        entity.setAcquiredDate(item.getAcquiredDate());
        return entity;
    }

    public Item toDomainModel(ItemEntity entity) {
        return Item.createForMapper(
            ItemId.of(entity.getId()),
            PublicationId.of(entity.getPublicationId()),
            Barcode.of(entity.getBarcode()),
            entity.getStatus(),
            entity.getItemType(),
            entity.getBranch(),
            entity.getShelf(),
            entity.getCondition(),
            entity.getAcquiredDate()
        );
    }
}
