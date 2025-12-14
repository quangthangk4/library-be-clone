package com.library.catalog.infrastructure.persistence.mapper;

import com.library.catalog.domain.entities.Publisher;
import com.library.catalog.domain.valueobject.PublisherId;
import com.library.catalog.infrastructure.persistence.entity.PublisherEntity;
import org.springframework.stereotype.Component;

@Component
public class PublisherEntityMapper {

    public PublisherEntity toEntity(Publisher publisher) {
        PublisherEntity entity = new PublisherEntity();
        entity.setId(publisher.getId().getValue());
        entity.setPublisherName(publisher.getPublisherName());
        entity.setAddress(publisher.getAddress());
        return entity;
    }

    public Publisher toDomainModel(PublisherEntity entity) {
        return Publisher.of(
            PublisherId.of(entity.getId()),
            entity.getPublisherName(),
            entity.getAddress()
        );
    }
}
