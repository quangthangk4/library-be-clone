package com.library.recommendation.domain.repository;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.recommendation.domain.entity.WishList;
import com.library.recommendation.domain.valueobject.WishListId;
import com.library.user.domain.valueobject.UserId;

import java.util.List;
import java.util.Optional;

public interface WishListRepository {
    WishList save(WishList wishList);
    Optional<WishList> findById(WishListId id);
    List<WishList> findByUserId(UserId userId);
    Optional<WishList> findByUserIdAndPublicationId(UserId userId, PublicationId publicationId);
    void delete(WishListId id);
}
