package com.library.catalog.api.controller;

import com.library.catalog.application.dto.request.CreatePublisherRequest;
import com.library.catalog.application.dto.request.UpdatePublisherRequest;
import com.library.catalog.application.dto.response.PublisherResponse;
import com.library.catalog.application.usecase.publisher.CreatePublisherUseCase;
import com.library.catalog.application.usecase.publisher.DeletePublisherUseCase;
import com.library.catalog.application.usecase.publisher.GetAllPublishersUseCase;
import com.library.catalog.application.usecase.publisher.GetPublisherByIdUseCase;
import com.library.catalog.application.usecase.publisher.UpdatePublisherUseCase;
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
 * REST Controller for Publisher management
 * Follows RESTFUL API design principles
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final CreatePublisherUseCase createPublisherUseCase;
    private final GetPublisherByIdUseCase getPublisherByIdUseCase;
    private final GetAllPublishersUseCase getAllPublishersUseCase;
    private final UpdatePublisherUseCase updatePublisherUseCase;
    private final DeletePublisherUseCase deletePublisherUseCase;

    /**
     * Create a new publisher
     * POST /api/v1/publishers
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<PublisherResponse> createPublisher(@Valid @RequestBody CreatePublisherRequest request) {
        log.info("REST request to create publisher: {}", request.publisherName());
        PublisherResponse response = createPublisherUseCase.execute(request);
        return ApiResponseApp.created("create publisher successfully", response);
    }

    /**
     * Get publisher by ID
     * GET /api/v1/publishers/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<PublisherResponse> getPublisherById(@PathVariable Long id) {
        log.info("REST request to get publisher by ID: {}", id);
        PublisherResponse response = getPublisherByIdUseCase.execute(id);
        return ApiResponseApp.success(response);
    }

    /**
     * Get all publishers
     * GET /api/v1/publishers
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<PublisherResponse>> getAllPublishers() {
        log.info("REST request to get all publishers");
        List<PublisherResponse> responses = getAllPublishersUseCase.execute();
        return ApiResponseApp.success(responses);
    }

    /**
     * Update publisher
     * PUT /api/v1/publishers/{id}
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<PublisherResponse> updatePublisher(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePublisherRequest request) {
        log.info("REST request to update publisher ID: {}", id);
        PublisherResponse response = updatePublisherUseCase.execute(id, request);
        return ApiResponseApp.success(response);
    }

    /**
     * Delete publisher
     * DELETE /api/v1/publishers/{id}
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<Void> deletePublisher(@PathVariable Long id) {
        log.info("REST request to delete publisher ID: {}", id);
        deletePublisherUseCase.execute(id);
        return ApiResponseApp.success(null);
    }
}
