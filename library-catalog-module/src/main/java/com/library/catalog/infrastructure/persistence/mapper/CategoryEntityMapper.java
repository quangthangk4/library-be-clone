package com.library.catalog.infrastructure.persistence.mapper;

import com.library.catalog.domain.entities.Category;
import com.library.catalog.domain.valueobject.CategoryId;
import com.library.catalog.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityMapper {

    public CategoryEntity toEntity(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId().getValue());
        entity.setCategoryName(category.getCategoryName());
        entity.setParentCategoryId(
            category.getParentCategoryId() != null
                ? category.getParentCategoryId().getValue()
                : null
        );
        return entity;
    }

    public Category toDomainModel(CategoryEntity entity) {
        return Category.of(
            CategoryId.of(entity.getId()),
            entity.getCategoryName(),
            entity.getParentCategoryId() != null
                ? CategoryId.of(entity.getParentCategoryId())
                : null
        );
    }
}
