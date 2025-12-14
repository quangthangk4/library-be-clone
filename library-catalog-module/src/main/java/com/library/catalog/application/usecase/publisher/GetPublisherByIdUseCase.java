package com.library.catalog.application.usecase.publisher;

import com.library.catalog.application.dto.response.PublisherResponse;

public interface GetPublisherByIdUseCase {
    PublisherResponse execute(Long id);
}
