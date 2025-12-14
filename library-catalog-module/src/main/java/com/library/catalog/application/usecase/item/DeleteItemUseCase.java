package com.library.catalog.application.usecase.item;

/**
 * Use case for deleting an item
 */
public interface DeleteItemUseCase {

    /**
     * Execute the use case to delete an item
     *
     * @param id the item ID
     */
    void execute(Long id);
}
