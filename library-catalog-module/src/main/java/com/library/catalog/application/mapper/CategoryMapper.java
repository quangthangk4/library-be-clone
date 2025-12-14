package com.library.catalog.application.mapper;

import com.library.catalog.application.dto.response.CategoryResponse;
import com.library.catalog.domain.entities.Category;
import com.library.catalog.domain.repository.CategoryRepository;
import com.library.catalog.infrastructure.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "parentCategoryId.value", target = "parentCategoryId")
    @Mapping(target = "parentCategoryName", ignore = true)
    CategoryResponse toResponse(Category category);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "parentCategoryId", target = "parentCategoryId")
    @Mapping(target = "parentCategoryName", ignore = true)
    CategoryResponse entityToResponse(CategoryEntity entity);


    default CategoryResponse toCategoryResponse(Category updatedCategory, CategoryRepository categoryRepository) {
        CategoryResponse response = this.toResponse(updatedCategory);
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
