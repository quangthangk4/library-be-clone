package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.response.ItemResponse;

/**
 * Use case for retrieving an item by ID
 */
public interface GetItemByIdUseCase {

    /**
     * Execute the use case to get an item by ID
     *
     * @param id the item ID
     * @return the item response
     */
    ItemResponse execute(Long id);
}
