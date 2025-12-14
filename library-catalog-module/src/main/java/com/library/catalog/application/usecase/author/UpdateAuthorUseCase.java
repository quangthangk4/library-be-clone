package com.library.catalog.application.usecase.author;

import com.library.catalog.application.dto.request.UpdateAuthorRequest;
import com.library.catalog.application.dto.response.AuthorResponse;

public interface UpdateAuthorUseCase {
    AuthorResponse execute(Long id, UpdateAuthorRequest request);
}
