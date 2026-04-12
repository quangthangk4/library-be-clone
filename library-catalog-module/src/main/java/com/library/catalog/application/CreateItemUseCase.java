package com.library.catalog.application;

import com.library.catalog.dto.request.item.CreateItemRequest;

public interface CreateItemUseCase {
    void execute(CreateItemRequest request);
}
