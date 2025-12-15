package com.library.catalog.application.usecase.publication;

import com.library.shared.dto.PageResponse;
import com.library.catalog.application.dto.response.PublicationResponse;

import java.util.List;

public interface GetAllPublicationsUseCase {
    PageResponse<PublicationResponse> execute(int page, int size);
    List<PublicationResponse> execute();
}
