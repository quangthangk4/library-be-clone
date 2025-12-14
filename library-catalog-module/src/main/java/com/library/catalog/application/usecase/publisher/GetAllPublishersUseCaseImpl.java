package com.library.catalog.application.usecase.publisher;

import com.library.catalog.application.dto.response.PublisherResponse;
import com.library.catalog.application.mapper.PublisherMapper;
import com.library.catalog.domain.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllPublishersUseCaseImpl implements GetAllPublishersUseCase {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PublisherResponse> execute() {
        log.info("Fetching all publishers");

        return publisherRepository.findAll().stream()
            .map(publisherMapper::toResponse)
            .collect(Collectors.toList());
    }
}
