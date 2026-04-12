package com.library.catalog.application.impl;

import com.library.catalog.application.GetPublicationByIdByLibrarianUseCase;
import com.library.catalog.dto.response.publication.LibrarianPublicationDetailResponse;
import com.library.catalog.infrastructure.persistence.repository.PublicationRepositoryCustom;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPublicationByIdByLibrarianUseCaseImpl implements GetPublicationByIdByLibrarianUseCase {
    private final PublicationRepositoryCustom publicationRepository;

    @Override
    public LibrarianPublicationDetailResponse execute(Long publicationId) {
        return publicationRepository
                .findPublicationDetailForLibrarian(publicationId)
                .orElseThrow(() -> new AppException(ErrorCode.PUBLICATION_NOT_FOUND));
    }
}

