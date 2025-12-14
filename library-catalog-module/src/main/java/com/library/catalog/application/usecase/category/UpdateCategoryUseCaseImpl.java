package com.library.catalog.application.usecase.category;

import com.library.catalog.application.dto.request.UpdateCategoryRequest;
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
public class UpdateCategoryUseCaseImpl implements UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse execute(Long id, UpdateCategoryRequest request) {
        log.info("Updating category with ID: {}", id);

        // Find existing category
        Category category = categoryRepository.findById(CategoryId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // Update category name if provided
        if (request.categoryName() != null) {
            // Check if new name already exists (and it's not the same category)
            categoryRepository.findByName(request.categoryName())
                .ifPresent(existingCategory -> {
                    if (!existingCategory.getId().equals(category.getId())) {
                        throw new AppException(ErrorCode.CATEGORY_NAME_ALREADY_EXISTS);
                    }
                });
            category.rename(request.categoryName());
        }

        // Update parent category if provided
        if (request.parentCategoryId() != null) {
            CategoryId newParentId = CategoryId.of(request.parentCategoryId());

            // Prevent circular reference (category cannot be its own parent)
            if (newParentId.equals(category.getId())) {
                throw new AppException(ErrorCode.CIRCULAR_CATEGORY_REFERENCE);
            }

            // Validate parent exists
            categoryRepository.findById(newParentId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_PARENT_CATEGORY));

            // Prevent circular reference (parent cannot be a child of this category)
            // This is a simplified check - a full implementation would recursively check all descendants
            categoryRepository.findById(newParentId).ifPresent(parent -> {
                if (parent.getParentCategoryId() != null && parent.getParentCategoryId().equals(category.getId())) {
                    throw new AppException(ErrorCode.CIRCULAR_CATEGORY_REFERENCE);
                }
            });

            category.changeParent(newParentId);
        }

        // Save updated category
        Category updatedCategory = categoryRepository.save(category);

        log.info("Category updated successfully with ID: {}", id);

        // Build response with parent name
        CategoryResponse response = categoryMapper.toResponse(updatedCategory);
        if (updatedCategory.getParentCategoryId() != null) {
            String parentName = categoryRepository.findById(updatedCategory.getParentCategoryId())
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
