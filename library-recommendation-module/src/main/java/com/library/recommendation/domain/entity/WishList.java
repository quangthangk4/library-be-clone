package com.library.recommendation.domain.entity;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.recommendation.domain.valueobject.WishListId;
import com.library.shared.entity.BaseDomainEntity;
import com.library.user.domain.valueobject.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WishList extends BaseDomainEntity {
    private WishListId id;
    private UserId userId;
    private PublicationId publicationId;

    public static WishList createForMapper(WishListId id, UserId userId, PublicationId publicationId) {
        return new WishList(id, userId, publicationId);
    }
}
