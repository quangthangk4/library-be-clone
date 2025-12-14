package com.library.catalog.application.usecase.tag;

import com.library.catalog.application.dto.request.CreateTagRequest;
import com.library.catalog.application.dto.response.TagResponse;

public interface CreateTagUseCase {
    TagResponse execute(CreateTagRequest request);
}
