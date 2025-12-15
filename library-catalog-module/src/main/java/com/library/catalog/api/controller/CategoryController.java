package com.library.catalog.api.controller;

import com.library.catalog.application.dto.request.CreateCategoryRequest;
import com.library.catalog.application.dto.request.UpdateCategoryRequest;
import com.library.catalog.application.dto.response.CategoryResponse;
import com.library.catalog.application.usecase.category.CreateCategoryUseCase;
import com.library.catalog.application.usecase.category.DeleteCategoryUseCase;
import com.library.catalog.application.usecase.category.GetAllCategoriesUseCase;
import com.library.catalog.application.usecase.category.GetCategoryByIdUseCase;
import com.library.catalog.application.usecase.category.UpdateCategoryUseCase;
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
 * REST Controller for Category management
 * Follows RESTFUL API design principles
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    /**
     * Create a new category
     * POST /api/v1/categories
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        log.info("REST request to create category: {}", request.categoryName());
        CategoryResponse response = createCategoryUseCase.execute(request);
        return ApiResponseApp.created("create category successfully", response);
    }

    /**
     * Get category by ID
     * GET /api/v1/categories/{id}
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<CategoryResponse> getCategoryById(@PathVariable Long id) {
        log.info("REST request to get category by ID: {}", id);
        CategoryResponse response = getCategoryByIdUseCase.execute(id);
        return ApiResponseApp.success(response);
    }

    /**
     * Get all categories
     * GET /api/v1/categories
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<List<CategoryResponse>> getAllCategories() {
        log.info("REST request to get all categories");
        List<CategoryResponse> responses = getAllCategoriesUseCase.execute();
        return ApiResponseApp.success(responses);
    }

    /**
     * Update category
     * PUT /api/v1/categories/{id}
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<CategoryResponse> updateCategory(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateCategoryRequest request) {
        log.info("REST request to update category ID: {}", id);
        CategoryResponse response = updateCategoryUseCase.execute(id, request);
        return ApiResponseApp.success(response);
    }

    /**
     * Delete category
     * DELETE /api/v1/categories/{id}
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseApp<Void> deleteCategory(@PathVariable Long id) {
        log.info("REST request to delete category ID: {}", id);
        deleteCategoryUseCase.execute(id);
        return ApiResponseApp.success(null);
    }
}
