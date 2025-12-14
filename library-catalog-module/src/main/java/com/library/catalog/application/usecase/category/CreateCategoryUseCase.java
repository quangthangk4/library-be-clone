package com.library.catalog.application.usecase.category;

import com.library.catalog.application.dto.request.CreateCategoryRequest;
import com.library.catalog.application.dto.response.CategoryResponse;

public interface CreateCategoryUseCase {
    CategoryResponse execute(CreateCategoryRequest request);
}
