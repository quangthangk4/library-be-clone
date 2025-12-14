package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.request.UpdateItemStatusRequest;
import com.library.catalog.application.dto.response.ItemResponse;

/**
 * Use case for updating item status
 */
public interface UpdateItemStatusUseCase {

    /**
     * Execute the use case to update item status
     *
     * @param id the item ID
     * @param request the update status request
     * @return the updated item response
     */
    ItemResponse execute(Long id, UpdateItemStatusRequest request);
}
