package com.library.user.infrastructure.persistence.mapper;

import com.library.user.domain.entities.SearchHistory;
import com.library.user.domain.valueobject.SearchId;
import com.library.user.domain.valueobject.UserId;
import com.library.user.infrastructure.persistence.entity.SearchHistoryEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between SearchHistoryEntity and SearchHistory domain model.
 */
@Component
public class SearchHistoryEntityMapper {

    /**
     * Convert domain model to entity.
     */
    public SearchHistoryEntity toEntity(SearchHistory searchHistory) {
        if (searchHistory == null) {
            return null;
        }

        SearchHistoryEntity entity = SearchHistoryEntity.builder()
            .userId(searchHistory.getUserId().getValue())
            .searchQuery(searchHistory.getSearchQuery())
            .timestamp(searchHistory.getTimestamp())
            .build();

        if (searchHistory.getId() != null) {
            entity.setId(searchHistory.getId().getValue());
        }

        return entity;
    }

    /**
     * Convert entity to domain model.
     */
    public SearchHistory toDomainModel(SearchHistoryEntity entity) {
        if (entity == null) {
            return null;
        }

        return SearchHistory.createForMapper(
            SearchId.of(entity.getId()),
            UserId.of(entity.getUserId()),
            entity.getSearchQuery(),
            entity.getTimestamp()
        );
    }
}
