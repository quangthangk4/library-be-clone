package com.library.catalog.application.usecase.category;

import com.library.catalog.application.dto.request.CreateCategoryRequest;
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
public class CreateCategoryUseCaseImpl implements CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse execute(CreateCategoryRequest request) {
        log.info("Creating category with name: {}", request.categoryName());

        // Check if category name already exists
        if (categoryRepository.existsByName(request.categoryName())) {
            throw new AppException(ErrorCode.CATEGORY_NAME_ALREADY_EXISTS);
        }

        // If parent category ID is provided, validate it exists
        Category category;
        if (request.parentCategoryId() != null) {
            CategoryId parentId = CategoryId.of(request.parentCategoryId());

            // Validate parent exists
            categoryRepository.findById(parentId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_PARENT_CATEGORY));

            category = Category.createChild(request.categoryName(), parentId);
        } else {
            category = Category.createRoot(request.categoryName());
        }

        // Save category
        Category savedCategory = categoryRepository.save(category);

        log.info("Category created successfully with ID: {}", savedCategory.getId().getValue());

        // Build response
        CategoryResponse response = categoryMapper.toResponse(savedCategory);

        // If has parent, fetch parent name
        if (savedCategory.getParentCategoryId() != null) {
            String parentName = categoryRepository.findById(savedCategory.getParentCategoryId())
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
