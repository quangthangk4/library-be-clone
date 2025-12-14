package com.library.catalog.application.usecase.tag;

import com.library.catalog.application.dto.response.TagResponse;

public interface GetTagByIdUseCase {
    TagResponse execute(Long id);
}
