package com.library.catalog.infrastructure.persistence.repository;

import com.library.catalog.dto.response.publication.LibrarianPublicationListResponse;
import com.library.catalog.dto.response.publication.PublicationDetailResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublicationRepositoryCustom {

  Page<LibrarianPublicationListResponse> findPublicationsForLibrarian(
      String keyword, Long categoryId, Integer year, Boolean hasItems, Pageable pageable
  );

  Optional<PublicationDetailResponse> findPublicationDetailForLibrarian(Long id);
}