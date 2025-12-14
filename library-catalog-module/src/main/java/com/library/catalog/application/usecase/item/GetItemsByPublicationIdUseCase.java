package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.response.ItemResponse;

import java.util.List;

/**
 * Use case for retrieving items by publication ID
 */
public interface GetItemsByPublicationIdUseCase {

    /**
     * Execute the use case to get items by publication ID
     *
     * @param publicationId the publication ID
     * @return list of item responses
     */
    List<ItemResponse> execute(Long publicationId);
}
