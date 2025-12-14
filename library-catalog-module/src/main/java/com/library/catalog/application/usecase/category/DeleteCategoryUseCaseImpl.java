package com.library.catalog.application.usecase.category;

import com.library.catalog.domain.repository.CategoryRepository;
import com.library.catalog.domain.repository.PublicationRepository;
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
public class DeleteCategoryUseCaseImpl implements DeleteCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final PublicationRepository publicationRepository;

    @Override
    @Transactional
    public void execute(Long id) {
        log.info("Deleting category with ID: {}", id);

        CategoryId categoryId = CategoryId.of(id);

        // Check if a category exists
        if (categoryRepository.findById(categoryId).isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        // Check if category has children
        if (categoryRepository.hasChildren(categoryId)) {
            throw new AppException(ErrorCode.CANNOT_DELETE_CATEGORY_HAS_CHILDREN);
        }

        // Check if category has publications
        if (!publicationRepository.findByCategoryId(categoryId).isEmpty()) {
            throw new AppException(ErrorCode.CANNOT_DELETE_CATEGORY_HAS_PUBLICATIONS);
        }

        // Delete category
        categoryRepository.deleteById(categoryId);

        log.info("Category deleted successfully with ID: {}", id);
    }
}
