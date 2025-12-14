package com.library.catalog.application.mapper;

import com.library.catalog.application.dto.response.CategoryResponse;
import com.library.catalog.domain.entities.Category;
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
}
