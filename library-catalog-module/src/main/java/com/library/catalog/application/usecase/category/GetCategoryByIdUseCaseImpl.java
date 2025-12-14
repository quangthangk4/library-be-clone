package com.library.catalog.application.usecase.category;

import com.library.catalog.application.dto.response.CategoryResponse;
import com.library.catalog.application.mapper.CategoryMapper;
import com.library.catalog.domain.entities.Category;
import com.library.catalog.domain.repository.CategoryRepository;
import com.library.catalog.domain.valueobject.CategoryId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCategoryByIdUseCaseImpl implements GetCategoryByIdUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse execute(Long id) {
        log.info("Fetching category with ID: {}", id);

        Category category = categoryRepository.findById(CategoryId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        CategoryResponse response = categoryMapper.toResponse(category);

        // If has parent, fetch parent name
        if (category.getParentCategoryId() != null) {
            String parentName = categoryRepository.findById(category.getParentCategoryId())
                .map(Category::getCategoryName)
                .orElse(null);
            response = new CategoryResponse(
                response.id(),
                response.categoryName(),
                response.parentCategoryId(),
                parentName,
                response.createdAt(),
                response.updatedAt()
            );
        }

        return response;
    }
}
