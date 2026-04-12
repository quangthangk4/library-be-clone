package com.library.catalog.application;

import com.library.catalog.dto.response.publication.LibrarianPublicationDetailResponse;

public interface GetPublicationByIdByLibrarianUseCase {
    LibrarianPublicationDetailResponse execute(Long publicationId);
}
