package com.library.catalog.application.usecase.author;

import com.library.catalog.application.dto.response.AuthorResponse;

public interface GetAuthorByIdUseCase {
    AuthorResponse execute(Long id);
}
