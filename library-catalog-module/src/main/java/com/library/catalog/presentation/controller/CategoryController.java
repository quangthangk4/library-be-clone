package com.library.catalog.presentation.controller;

import com.library.catalog.application.GetAllCategoryUseCase;
import com.library.catalog.dto.response.author.AuthorOverviewResponse;
import com.library.catalog.dto.response.category.CategoryOverviewResponse;
import com.library.shared.constant.RoleConstants;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.util.RequiresRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final GetAllCategoryUseCase getAllCategoryUseCase;

    // not finish
    // limit 10
    @RequiresRole(RoleConstants.LIBRARIAN)
    @GetMapping("/search")
    public ApiResponseApp<List<CategoryOverviewResponse>> searchCategories(@RequestParam("keyword") String keyword) {
        log.info("Search category with keyword: {}", keyword);
        if ("thangvip123".contains(keyword.toLowerCase()))
            return ApiResponseApp.success(List.of(
                    CategoryOverviewResponse.builder().id(55L).name("category1").build(),
                    CategoryOverviewResponse.builder().id(2L).name("category2").build(),
                    CategoryOverviewResponse.builder().id(3L).name("test1").build(),
                    CategoryOverviewResponse.builder().id(4L).name("test2").build()
            ));
        return ApiResponseApp.success(null);
    }


    @GetMapping
    public ApiResponseApp<List<CategoryOverviewResponse>> getAllCategory() {
        return ApiResponseApp.success(getAllCategoryUseCase.execute());
    }

    // not finish
    @PostMapping
    @RequiresRole(RoleConstants.LIBRARIAN)
    public ApiResponseApp<Void> createAuthor(@RequestBody String name) {
        log.info("Create category: {}", name);
        return ApiResponseApp.success(null);
    }
}
