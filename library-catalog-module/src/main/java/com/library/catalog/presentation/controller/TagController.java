package com.library.catalog.presentation.controller;

import com.library.catalog.application.dto.request.CreateTagRequest;
import com.library.catalog.application.dto.response.TagResponse;
import com.library.catalog.application.usecase.tag.CreateTagUseCase;
import com.library.catalog.application.usecase.tag.DeleteTagUseCase;
import com.library.catalog.application.usecase.tag.GetAllTagsUseCase;
import com.library.catalog.application.usecase.tag.GetTagByIdUseCase;
import com.library.shared.dto.ApiResponseApp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Tag management
 * Follows RESTFUL API design principles
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final CreateTagUseCase createTagUseCase;
    private final GetTagByIdUseCase getTagByIdUseCase;
    private final GetAllTagsUseCase getAllTagsUseCase;
    private final DeleteTagUseCase deleteTagUseCase;

    /**
     * Create a new tag
     * POST /api/v1/tags
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<TagResponse> createTag(@Valid @RequestBody CreateTagRequest request) {
        log.info("REST request to create tag: {}", request.tagName());
        TagResponse response = createTagUseCase.execute(request);
        return ApiResponseApp.created("create tag successfully", response);
    }

    /**
     * Get tag by ID
     * GET /api/v1/tags/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<TagResponse> getTagById(@PathVariable Long id) {
        log.info("REST request to get tag by ID: {}", id);
        TagResponse response = getTagByIdUseCase.execute(id);
        return ApiResponseApp.success(response);
    }

    /**
     * Get all tags
     * GET /api/v1/tags
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<TagResponse>> getAllTags() {
        log.info("REST request to get all tags");
        List<TagResponse> responses = getAllTagsUseCase.execute();
        return ApiResponseApp.success(responses);
    }

    /**
     * Delete tag
     * DELETE /api/v1/tags/{id}
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<Void> deleteTag(@PathVariable Long id) {
        log.info("REST request to delete tag ID: {}", id);
        deleteTagUseCase.execute(id);
        return ApiResponseApp.success(null);
    }
}
