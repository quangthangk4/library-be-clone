package com.library.catalog.application.usecase.publisher;

import com.library.catalog.application.dto.request.CreatePublisherRequest;
import com.library.catalog.application.dto.response.PublisherResponse;

public interface CreatePublisherUseCase {
    PublisherResponse execute(CreatePublisherRequest request);
}
