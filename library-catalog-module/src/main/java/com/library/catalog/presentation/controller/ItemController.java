package com.library.catalog.presentation.controller;

import com.library.catalog.application.GetItemByIdUseCase;
import com.library.catalog.application.GetListItemWithFilterUseCase;
import com.library.catalog.dto.request.item.ItemSearchRequest;
import com.library.catalog.dto.response.item.ItemDetailResponse;
import com.library.shared.constant.RoleConstants;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.dto.PageResponse;
import com.library.shared.util.RequiresRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final GetListItemWithFilterUseCase getListItemWithFilterUseCase;
    private final GetItemByIdUseCase getItemByIdUseCase;

    @RequiresRole(RoleConstants.LIBRARIAN)
    @GetMapping
    public ApiResponseApp<PageResponse<ItemDetailResponse>> findAllItemWithFilter(
            @ModelAttribute ItemSearchRequest request
    ) {
        log.info("Find all item with filter: {}", request);
        return ApiResponseApp.success(getListItemWithFilterUseCase.execute(request));
    }


    @GetMapping("/{id}")
    public ApiResponseApp<ItemDetailResponse> findItemById(
            @PathVariable("id") Long id
    ) {
        log.info("Find item by id: {}", id);
        return ApiResponseApp.success(getItemByIdUseCase.execute(id));
    }
}
