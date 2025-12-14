package com.library.catalog.application.usecase.author;

import com.library.catalog.application.dto.response.AuthorResponse;
import com.library.catalog.application.mapper.AuthorMapper;
import com.library.catalog.domain.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllAuthorsUseCaseImpl implements GetAllAuthorsUseCase {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponse> execute() {
        log.info("Fetching all authors");

        return authorRepository.findAll().stream()
            .map(authorMapper::toResponse)
            .collect(Collectors.toList());
    }
}
