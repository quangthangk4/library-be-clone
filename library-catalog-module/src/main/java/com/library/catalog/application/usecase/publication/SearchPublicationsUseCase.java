package com.library.catalog.application.usecase.publication;

import com.library.catalog.application.dto.request.SearchPublicationRequest;
import com.library.shared.dto.PageResponse;
import com.library.catalog.application.dto.response.PublicationResponse;

public interface SearchPublicationsUseCase {
    PageResponse<PublicationResponse> execute(SearchPublicationRequest request);
}
