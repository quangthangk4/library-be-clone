package com.library.catalog.presentation.controller;

import com.library.catalog.application.dto.request.CreateItemRequest;
import com.library.catalog.application.dto.request.UpdateItemLocationRequest;
import com.library.catalog.application.dto.request.UpdateItemStatusRequest;
import com.library.catalog.application.dto.response.ItemResponse;
import com.library.catalog.application.dto.response.ItemWithPublicationResponse;
import com.library.catalog.application.usecase.item.CreateItemUseCase;
import com.library.catalog.application.usecase.item.DeleteItemUseCase;
import com.library.catalog.application.usecase.item.GetAllItemsUseCase;
import com.library.catalog.application.usecase.item.GetItemByIdUseCase;
import com.library.catalog.application.usecase.item.GetItemsByPublicationIdUseCase;
import com.library.catalog.application.usecase.item.UpdateItemLocationUseCase;
import com.library.catalog.application.usecase.item.UpdateItemStatusUseCase;
import com.library.shared.dto.ApiResponseApp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Item management
 * Follows RESTFUL API design principles
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final CreateItemUseCase createItemUseCase;
    private final GetItemByIdUseCase getItemByIdUseCase;
    private final GetAllItemsUseCase getAllItemsUseCase;
    private final DeleteItemUseCase deleteItemUseCase;
    private final GetItemsByPublicationIdUseCase getItemsByPublicationIdUseCase;
    private final UpdateItemLocationUseCase updateItemLocationUseCase;
    private final UpdateItemStatusUseCase updateItemStatusUseCase;

    /**
     * Create a new item
     * POST /api/v1/items
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<ItemResponse> createItem(@Valid @RequestBody CreateItemRequest request) {
        log.info("REST request to create item for publication ID: {}", request.publicationId());
        ItemResponse response = createItemUseCase.execute(request);
        return ApiResponseApp.created("create item successfully", response);
    }

    /**
     * Get item by ID
     * GET /api/v1/items/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<ItemWithPublicationResponse> getItemById(@PathVariable("id") Long id) {
        log.info("REST request to get item by ID: {}", id);
        ItemWithPublicationResponse response = getItemByIdUseCase.execute(id);
        return ApiResponseApp.success(response);
    }

    /**
     * Get all items
     * GET /api/v1/items
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<ItemResponse>> getAllItems() {
        log.info("REST request to get all items");
        List<ItemResponse> responses = getAllItemsUseCase.execute();
        return ApiResponseApp.success(responses);
    }

    /**
     * Delete item
     * DELETE /api/v1/items/{id}
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<Void> deleteItem(@PathVariable("id") Long id) {
        log.info("REST request to delete item ID: {}", id);
        deleteItemUseCase.execute(id);
        return ApiResponseApp.success(null);
    }

    /**
     * Get items by publication ID
     * GET /api/v1/items/publication/{publicationId}
     */
    @GetMapping("/publication/{publicationId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<ItemResponse>> getItemsByPublicationId(@PathVariable Long publicationId) {
        log.info("REST request to get items by publication ID: {}", publicationId);
        List<ItemResponse> responses = getItemsByPublicationIdUseCase.execute(publicationId);
        return ApiResponseApp.success(responses);
    }

    /**
     * Update item location
     * PUT /api/v1/items/{id}/location
     */
    @PutMapping("/{id}/location")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<ItemResponse> updateItemLocation(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateItemLocationRequest request) {
        log.info("REST request to update location for item ID: {}", id);
        ItemResponse response = updateItemLocationUseCase.execute(id, request);
        return ApiResponseApp.success(response);
    }

    /**
     * Update item status
     * PUT /api/v1/items/{id}/status
     */
    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<ItemResponse> updateItemStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateItemStatusRequest request) {
        log.info("REST request to update status for item ID: {}", id);
        ItemResponse response = updateItemStatusUseCase.execute(id, request);
        return ApiResponseApp.success(response);
    }
}
