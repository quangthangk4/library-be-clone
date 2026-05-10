package com.library.catalog.application;

import com.library.catalog.dto.request.publication.PublicSearchRequest;
import com.library.catalog.dto.response.publication.PublicSearchResult;
import com.library.shared.dto.PageResponse;

public interface SearchPublicationsUseCase {
    PageResponse<PublicSearchResult> execute(PublicSearchRequest request);
}
