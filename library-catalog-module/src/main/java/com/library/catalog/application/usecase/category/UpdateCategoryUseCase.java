package com.library.catalog.application.usecase.category;

import com.library.catalog.application.dto.request.UpdateCategoryRequest;
import com.library.catalog.application.dto.response.CategoryResponse;

public interface UpdateCategoryUseCase {
    CategoryResponse execute(Long id, UpdateCategoryRequest request);
}
