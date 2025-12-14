package com.library.catalog.application.usecase.publisher;

import com.library.catalog.application.dto.request.CreatePublisherRequest;
import com.library.catalog.application.dto.response.PublisherResponse;
import com.library.catalog.application.mapper.PublisherMapper;
import com.library.catalog.domain.entities.Publisher;
import com.library.catalog.domain.repository.PublisherRepository;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePublisherUseCaseImpl implements CreatePublisherUseCase {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Override
    @Transactional
    public PublisherResponse execute(CreatePublisherRequest request) {
        log.info("Creating publisher with name: {}", request.publisherName());

        // Check if publisher name already exists
        if (publisherRepository.existsByName(request.publisherName())) {
            throw new AppException(ErrorCode.PUBLISHER_NAME_ALREADY_EXISTS);
        }

        // Create publisher entity
        Publisher publisher = Publisher.create(
            request.publisherName(),
            request.address()
        );

        // Save publisher
        Publisher savedPublisher = publisherRepository.save(publisher);

        log.info("Publisher created successfully with ID: {}", savedPublisher.getId().getValue());
        return publisherMapper.toResponse(savedPublisher);
    }
}
