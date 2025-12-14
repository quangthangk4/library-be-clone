package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.request.CreateItemRequest;
import com.library.catalog.application.dto.response.ItemResponse;

/**
 * Use case for creating a new item
 */
public interface CreateItemUseCase {

    /**
     * Execute the use case to create a new item
     *
     * @param request the item creation request
     * @return the created item response
     */
    ItemResponse execute(CreateItemRequest request);
}
