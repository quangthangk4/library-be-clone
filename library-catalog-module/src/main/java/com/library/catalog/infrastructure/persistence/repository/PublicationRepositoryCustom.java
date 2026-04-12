package com.library.catalog.infrastructure.persistence.repository;

import com.library.catalog.dto.response.publication.LibrarianPublicationDetailResponse;
import com.library.catalog.dto.response.publication.LibrarianPublicationListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PublicationRepositoryCustom {
    Page<LibrarianPublicationListResponse> findPublicationsForLibrarian(
            String keyword, Long categoryId, Integer year, Boolean hasItems, Pageable pageable
    );

    Optional<LibrarianPublicationDetailResponse> findPublicationDetailForLibrarian(Long id);
}