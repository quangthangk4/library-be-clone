package com.library.catalog.application.usecase.publication;

import com.library.catalog.application.dto.request.GetAllPublicationForLibrarian;
import com.library.shared.dto.PageResponse;
import com.library.catalog.application.dto.response.PublicationResponse;

public interface GetAllPublicationsForLibrarianUseCase {
    PageResponse<PublicationResponse> execute(GetAllPublicationForLibrarian request);
}
