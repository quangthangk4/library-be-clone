package com.library.catalog.application.usecase.publication;

import com.library.catalog.application.dto.request.UpdatePublicationRequest;
import com.library.catalog.application.dto.response.PublicationResponse;

public interface UpdatePublicationUseCase {
    PublicationResponse execute(Long id, UpdatePublicationRequest request);
}
