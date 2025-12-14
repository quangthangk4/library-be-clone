package com.library.catalog.application.usecase.tag;

import com.library.catalog.application.dto.response.TagResponse;

import java.util.List;

public interface GetAllTagsUseCase {
    List<TagResponse> execute();
}
