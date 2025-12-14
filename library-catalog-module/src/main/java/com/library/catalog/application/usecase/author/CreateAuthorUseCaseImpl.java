package com.library.catalog.application.usecase.author;

import com.library.catalog.application.dto.request.CreateAuthorRequest;
import com.library.catalog.application.dto.response.AuthorResponse;
import com.library.catalog.application.mapper.AuthorMapper;
import com.library.catalog.domain.entities.Author;
import com.library.catalog.domain.repository.AuthorRepository;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAuthorUseCaseImpl implements CreateAuthorUseCase {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional
    public AuthorResponse execute(CreateAuthorRequest request) {
        log.info("Creating author with name: {}", request.authorName());

        // Check if the author name already exists
        if (authorRepository.existsByName(request.authorName())) {
            throw new AppException(ErrorCode.AUTHOR_ALREADY_EXISTS);
        }

        // Create an author entity
        Author author = Author.create(
            request.authorName(),
            request.biography(),
            request.dateOfBirth(),
            request.dateOfDeath()
        );

        // Save author
        Author savedAuthor = authorRepository.save(author);

        log.info("Author created successfully with ID: {}", savedAuthor.getId().getValue());
        return authorMapper.toResponse(savedAuthor);
    }
}
