package com.library.catalog.application.usecase.publisher;

import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.repository.PublisherRepository;
import com.library.catalog.domain.valueobject.PublisherId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletePublisherUseCaseImpl implements DeletePublisherUseCase {

    private final PublisherRepository publisherRepository;
    private final PublicationRepository publicationRepository;

    @Override
    @Transactional
    public void execute(Long id) {
        log.info("Deleting publisher with ID: {}", id);

        PublisherId publisherId = PublisherId.of(id);

        // Check if publisher exists
        if (!publisherRepository.findById(publisherId).isPresent()) {
            throw new AppException(ErrorCode.PUBLISHER_NOT_FOUND);
        }

        // Check if publisher has publications
        if (!publicationRepository.findByPublisherId(publisherId).isEmpty()) {
            throw new AppException(ErrorCode.CANNOT_DELETE_PUBLISHER_HAS_PUBLICATIONS);
        }

        // Delete publisher
        publisherRepository.deleteById(publisherId);

        log.info("Publisher deleted successfully with ID: {}", id);
    }
}
