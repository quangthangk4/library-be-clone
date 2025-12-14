package com.library.catalog.application.usecase.category;

import com.library.catalog.application.dto.response.CategoryResponse;

import java.util.List;

public interface GetAllCategoriesUseCase {
    List<CategoryResponse> execute();
}
