package com.library.catalog.application;

import com.library.catalog.dto.response.publication.PublicationDetailResponse;

public interface GetPublicationByIdByUseCase {

  PublicationDetailResponse execute(Long publicationId);
}
