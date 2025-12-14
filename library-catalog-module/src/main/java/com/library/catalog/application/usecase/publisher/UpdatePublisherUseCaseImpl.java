package com.library.catalog.application.usecase.publisher;

import com.library.catalog.application.dto.request.UpdatePublisherRequest;
import com.library.catalog.application.dto.response.PublisherResponse;
import com.library.catalog.application.mapper.PublisherMapper;
import com.library.catalog.domain.entities.Publisher;
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
public class UpdatePublisherUseCaseImpl implements UpdatePublisherUseCase {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Override
    @Transactional
    public PublisherResponse execute(Long id, UpdatePublisherRequest request) {
        log.info("Updating publisher with ID: {}", id);

        // Find existing publisher
        Publisher publisher = publisherRepository.findById(PublisherId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));

        // Update fields if provided
        if (request.publisherName() != null) {
            // Check if new name already exists (and it's not the same publisher)
            publisherRepository.findByName(request.publisherName())
                .ifPresent(existingPublisher -> {
                    if (!existingPublisher.getId().equals(publisher.getId())) {
                        throw new AppException(ErrorCode.PUBLISHER_NAME_ALREADY_EXISTS);
                    }
                });
            publisher.updateInfo(request.publisherName(), request.address());
        }

        // Save updated publisher
        Publisher updatedPublisher = publisherRepository.save(publisher);

        log.info("Publisher updated successfully with ID: {}", id);
        return publisherMapper.toResponse(updatedPublisher);
    }
}
