package com.library.catalog.application.usecase.publication;

import com.library.catalog.application.dto.response.PublicationResponse;

public interface GetPublicationByIdUseCase {
    PublicationResponse execute(Long id);
}
