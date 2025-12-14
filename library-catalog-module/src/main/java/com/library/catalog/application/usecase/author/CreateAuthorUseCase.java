package com.library.catalog.application.usecase.author;

import com.library.catalog.application.dto.request.CreateAuthorRequest;
import com.library.catalog.application.dto.response.AuthorResponse;

public interface CreateAuthorUseCase {
    AuthorResponse execute(CreateAuthorRequest request);
}
