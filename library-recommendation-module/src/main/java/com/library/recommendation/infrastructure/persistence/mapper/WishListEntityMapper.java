package com.library.recommendation.infrastructure.persistence.mapper;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.recommendation.domain.entity.WishList;
import com.library.recommendation.domain.valueobject.WishListId;
import com.library.recommendation.infrastructure.persistence.entity.WishListEntity;
import com.library.user.domain.valueobject.UserId;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between WishListEntity and WishList domain model.
 */
@Component
public class WishListEntityMapper {

    /**
     * Convert domain model to entity.
     */
    public WishListEntity toEntity(WishList wishList) {
        if (wishList == null) {
            return null;
        }

        WishListEntity entity = WishListEntity.builder()
            .userId(wishList.getUserId().getValue())
            .publicationId(wishList.getPublicationId().getValue())
            .build();

        if (wishList.getId() != null) {
            entity.setId(wishList.getId().getValue());
        }

        return entity;
    }

    /**
     * Convert entity to domain model.
     */
    public WishList toDomainModel(WishListEntity entity) {
        if (entity == null) {
            return null;
        }

        return WishList.createForMapper(
            WishListId.of(entity.getId()),
            UserId.of(entity.getUserId()),
            PublicationId.of(entity.getPublicationId())
        );
    }
}
