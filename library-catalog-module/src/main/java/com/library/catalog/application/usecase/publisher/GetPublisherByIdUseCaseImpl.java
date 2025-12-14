package com.library.catalog.application.usecase.publisher;

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
public class GetPublisherByIdUseCaseImpl implements GetPublisherByIdUseCase {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Override
    @Transactional(readOnly = true)
    public PublisherResponse execute(Long id) {
        log.info("Fetching publisher with ID: {}", id);

        Publisher publisher = publisherRepository.findById(PublisherId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));

        return publisherMapper.toResponse(publisher);
    }
}
