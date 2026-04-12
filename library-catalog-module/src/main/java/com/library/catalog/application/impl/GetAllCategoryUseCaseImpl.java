package com.library.catalog.application.impl;

import com.library.catalog.application.GetAllCategoryUseCase;
import com.library.catalog.application.mapper.CategoryMapper;
import com.library.catalog.domain.repository.CategoryRepository;
import com.library.catalog.dto.response.category.CategoryOverviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCategoryUseCaseImpl implements GetAllCategoryUseCase {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryOverviewResponse> execute() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryOverviewResponse).toList();
    }
}
