package com.library.catalog.application.usecase.item;

import com.library.catalog.application.dto.response.ItemResponse;

import java.util.List;

/**
 * Use case for retrieving all items
 */
public interface GetAllItemsUseCase {

    /**
     * Execute the use case to get all items
     *
     * @return list of item responses
     */
    List<ItemResponse> execute();
}
