package com.library.catalog.application.usecase.publisher;

import com.library.catalog.application.dto.response.PublisherResponse;

import java.util.List;

public interface GetAllPublishersUseCase {
    List<PublisherResponse> execute();
}
