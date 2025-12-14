package com.library.catalog.application.usecase.publisher;

import com.library.catalog.application.dto.request.UpdatePublisherRequest;
import com.library.catalog.application.dto.response.PublisherResponse;

public interface UpdatePublisherUseCase {
    PublisherResponse execute(Long id, UpdatePublisherRequest request);
}
