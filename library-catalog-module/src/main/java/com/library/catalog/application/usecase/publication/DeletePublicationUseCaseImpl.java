package com.library.catalog.application.usecase.publication;

import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.service.PublicationDomainService;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletePublicationUseCaseImpl implements DeletePublicationUseCase {

    private final PublicationRepository publicationRepository;
    private final PublicationDomainService publicationDomainService;

    @Override
    @Transactional
    public void execute(Long id) {
        log.info("Deleting publication with ID: {}", id);

        PublicationId publicationId = PublicationId.of(id);

        // Check if publication exists
        if (publicationRepository.findById(publicationId).isEmpty()) {
            throw new AppException(ErrorCode.PUBLICATION_NOT_FOUND);
        }

        // Check if publication can be deleted (no items)
        publicationDomainService.validateCanDeletePublication(publicationId);

        // Delete publication
        publicationRepository.deleteById(publicationId);

        log.info("Publication deleted successfully with ID: {}", id);
    }
}
