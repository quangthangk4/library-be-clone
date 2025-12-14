package com.library.catalog.application.usecase.publication;

import com.library.catalog.application.dto.request.CreatePublicationRequest;
import com.library.catalog.application.dto.response.PublicationResponse;

public interface CreatePublicationUseCase {
    PublicationResponse execute(CreatePublicationRequest request);
}
