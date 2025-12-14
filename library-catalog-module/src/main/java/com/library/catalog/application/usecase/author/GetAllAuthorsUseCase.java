package com.library.catalog.application.usecase.author;

import com.library.catalog.application.dto.response.AuthorResponse;

import java.util.List;

public interface GetAllAuthorsUseCase {
    List<AuthorResponse> execute();
}
