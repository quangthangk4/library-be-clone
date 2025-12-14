package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.request.UpdateItemLocationRequest;
import com.library.catalog.application.dto.response.ItemResponse;

/**
 * Use case for updating item location
 */
public interface UpdateItemLocationUseCase {

    /**
     * Execute the use case to update item location
     *
     * @param id the item ID
     * @param request the update location request
     * @return the updated item response
     */
    ItemResponse execute(Long id, UpdateItemLocationRequest request);
}
