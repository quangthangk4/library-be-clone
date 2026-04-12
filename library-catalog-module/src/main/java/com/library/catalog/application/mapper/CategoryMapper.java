package com.library.catalog.application.mapper;

import com.library.catalog.domain.entities.Category;
import com.library.catalog.dto.response.category.CategoryOverviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", source = "id.value")
    CategoryOverviewResponse toCategoryOverviewResponse(Category category);
}
