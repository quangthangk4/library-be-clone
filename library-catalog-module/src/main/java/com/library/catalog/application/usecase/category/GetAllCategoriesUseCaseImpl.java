package com.library.catalog.application.usecase.category;

import com.library.catalog.application.dto.response.CategoryResponse;
import com.library.catalog.application.mapper.CategoryMapper;
import com.library.catalog.domain.entities.Category;
import com.library.catalog.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllCategoriesUseCaseImpl implements GetAllCategoriesUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> execute() {
        log.info("Fetching all categories");

        List<Category> categories = categoryRepository.findAll();

        // Create a map of category ID to name for quick lookup
        Map<Long, String> categoryNameMap = categories.stream()
            .collect(Collectors.toMap(
                c -> c.getId().getValue(),
                Category::getCategoryName
            ));

        // Map to response with parent names
        return categories.stream()
            .map(category -> {
                CategoryResponse response = categoryMapper.toResponse(category);
                String parentName = category.getParentCategoryId() != null
                    ? categoryNameMap.get(category.getParentCategoryId().getValue())
                    : null;
                return new CategoryResponse(
                    response.id(),
                    response.categoryName(),
                    response.parentCategoryId(),
                    parentName,
                    response.createdAt(),
                    response.updatedAt()
                );
            })
            .collect(Collectors.toList());
    }
}
