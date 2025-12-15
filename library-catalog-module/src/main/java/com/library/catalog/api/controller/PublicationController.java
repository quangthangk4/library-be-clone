package com.library.catalog.api.controller;

import com.library.catalog.application.dto.request.CreatePublicationRequest;
import com.library.catalog.application.dto.request.SearchPublicationRequest;
import com.library.catalog.application.dto.request.UpdatePublicationRequest;
import com.library.shared.dto.PageResponse;
import com.library.catalog.application.dto.response.PublicationResponse;
import com.library.catalog.application.usecase.publication.CreatePublicationUseCase;
import com.library.catalog.application.usecase.publication.DeletePublicationUseCase;
import com.library.catalog.application.usecase.publication.GetAllPublicationsUseCase;
import com.library.catalog.application.usecase.publication.GetPublicationByIdUseCase;
import com.library.catalog.application.usecase.publication.SearchPublicationsUseCase;
import com.library.catalog.application.usecase.publication.UpdatePublicationUseCase;
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
 * REST Controller for Publication management
 * Follows RESTFUL API design principles
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
public class PublicationController {

    private final CreatePublicationUseCase createPublicationUseCase;
    private final GetPublicationByIdUseCase getPublicationByIdUseCase;
    private final GetAllPublicationsUseCase getAllPublicationsUseCase;
    private final UpdatePublicationUseCase updatePublicationUseCase;
    private final DeletePublicationUseCase deletePublicationUseCase;
    private final SearchPublicationsUseCase searchPublicationsUseCase;

    /**
     * Create a new publication
     * POST /api/v1/publications
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<PublicationResponse> createPublication(@Valid @RequestBody CreatePublicationRequest request) {
        log.info("REST request to create publication: {}", request.title());
        PublicationResponse response = createPublicationUseCase.execute(request);
        return ApiResponseApp.created("create publication successfully", response);
    }

    /**
     * Get publication by ID
     * GET /api/v1/publications/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<PublicationResponse> getPublicationById(@PathVariable("id") Long id) {
        log.info("REST request to get publication by ID: {}", id);
        PublicationResponse response = getPublicationByIdUseCase.execute(id);
        return ApiResponseApp.success(response);
    }

    /**
     * Get all publications
     * GET /api/v1/publications
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<PublicationResponse>> getAllPublications() {
        log.info("REST request to get all publications");
        List<PublicationResponse> responses = getAllPublicationsUseCase.execute();
        return ApiResponseApp.success(responses);
    }

    /**
     * Update publication
     * PUT /api/v1/publications/{id}
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<PublicationResponse> updatePublication(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdatePublicationRequest request) {
        log.info("REST request to update publication ID: {}", id);
        PublicationResponse response = updatePublicationUseCase.execute(id, request);
        return ApiResponseApp.success(response);
    }

    /**
     * Delete publication
     * DELETE /api/v1/publications/{id}
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<Void> deletePublication(@PathVariable("id") Long id) {
        log.info("REST request to delete publication ID: {}", id);
        deletePublicationUseCase.execute(id);
        return ApiResponseApp.success(null);
    }

    /**
     * Search publications
     * GET /api/v1/publications/search
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<PageResponse<PublicationResponse>> searchPublications(
            @Valid SearchPublicationRequest request) {
        log.info("REST request to search publications with criteria: {}", request);
        PageResponse<PublicationResponse> response = searchPublicationsUseCase.execute(request);
        return ApiResponseApp.success(response);
    }
}
