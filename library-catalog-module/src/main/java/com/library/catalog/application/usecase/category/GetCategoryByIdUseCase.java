package com.library.catalog.application.usecase.category;

import com.library.catalog.application.dto.response.CategoryResponse;

public interface GetCategoryByIdUseCase {
    CategoryResponse execute(Long id);
}
