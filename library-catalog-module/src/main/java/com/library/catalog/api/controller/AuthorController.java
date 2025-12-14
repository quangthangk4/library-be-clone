package com.library.catalog.api.controller;

import com.library.catalog.application.dto.request.CreateAuthorRequest;
import com.library.catalog.application.dto.request.UpdateAuthorRequest;
import com.library.catalog.application.dto.response.AuthorResponse;
import com.library.catalog.application.usecase.author.CreateAuthorUseCase;
import com.library.catalog.application.usecase.author.DeleteAuthorUseCase;
import com.library.catalog.application.usecase.author.GetAllAuthorsUseCase;
import com.library.catalog.application.usecase.author.GetAuthorByIdUseCase;
import com.library.catalog.application.usecase.author.UpdateAuthorUseCase;
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
 * REST Controller for Author management
 * Follows RESTFUL API design principles
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final CreateAuthorUseCase createAuthorUseCase;
    private final GetAuthorByIdUseCase getAuthorByIdUseCase;
    private final GetAllAuthorsUseCase getAllAuthorsUseCase;
    private final UpdateAuthorUseCase updateAuthorUseCase;
    private final DeleteAuthorUseCase deleteAuthorUseCase;

    /**
     * Create a new author
     * POST /api/v1/authors
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<AuthorResponse> createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
        log.info("REST request to create author: {}", request.name());
        AuthorResponse response = createAuthorUseCase.execute(request);
        return ApiResponseApp.created("create author successfully", response);
    }

    /**
     * Get author by ID
     * GET /api/v1/authors/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<AuthorResponse> getAuthorById(@PathVariable Long id) {
        log.info("REST request to get author by ID: {}", id);
        AuthorResponse response = getAuthorByIdUseCase.execute(id);
        return ApiResponseApp.success(response);
    }

    /**
     * Get all authors
     * GET /api/v1/authors
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<AuthorResponse>> getAllAuthors() {
        log.info("REST request to get all authors");
        List<AuthorResponse> responses = getAllAuthorsUseCase.execute();
        return ApiResponseApp.success(responses);
    }

    /**
     * Update author
     * PUT /api/v1/authors/{id}
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAuthorRequest request) {
        log.info("REST request to update author ID: {}", id);
        AuthorResponse response = updateAuthorUseCase.execute(id, request);
        return ApiResponseApp.success(response);
    }

    /**
     * Delete author
     * DELETE /api/v1/authors/{id}
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<Void> deleteAuthor(@PathVariable Long id) {
        log.info("REST request to delete author ID: {}", id);
        deleteAuthorUseCase.execute(id);
        return ApiResponseApp.success(null);
    }
}
