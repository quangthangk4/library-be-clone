package com.library.catalog.infrastructure.persistence.mapper;

import com.library.catalog.domain.entities.Tag;
import com.library.catalog.domain.valueobject.TagId;
import com.library.catalog.infrastructure.persistence.entity.TagEntity;
import org.springframework.stereotype.Component;

@Component
public class TagEntityMapper {

    public TagEntity toEntity(Tag tag) {
        TagEntity entity = new TagEntity();
        entity.setId(tag.getId().getValue());
        entity.setTagName(tag.getTagName());
        return entity;
    }

    public Tag toDomainModel(TagEntity entity) {
        return Tag.of(
            TagId.of(entity.getId()),
            entity.getTagName()
        );
    }
}
