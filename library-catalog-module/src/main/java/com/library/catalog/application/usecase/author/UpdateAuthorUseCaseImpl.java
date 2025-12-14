package com.library.catalog.application.usecase.author;

import com.library.catalog.application.dto.request.UpdateAuthorRequest;
import com.library.catalog.application.dto.response.AuthorResponse;
import com.library.catalog.application.mapper.AuthorMapper;
import com.library.catalog.domain.entities.Author;
import com.library.catalog.domain.repository.AuthorRepository;
import com.library.catalog.domain.valueobject.AuthorId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateAuthorUseCaseImpl implements UpdateAuthorUseCase {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional
    public AuthorResponse execute(Long id, UpdateAuthorRequest request) {
        log.info("Updating author with ID: {}", id);

        // Find existing author
        Author author = authorRepository.findById(AuthorId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        // Update fields if provided
        if (request.authorName() != null) {
            // Check if new name already exists (and it's not the same author)
            authorRepository.findByName(request.authorName())
                .ifPresent(existingAuthor -> {
                    if (!existingAuthor.getId().equals(author.getId())) {
                        throw new AppException(ErrorCode.AUTHOR_NAME_ALREADY_EXISTS);
                    }
                });
            author.updateName(request.authorName());
        }

        if (request.biography() != null) {
            author.updateBiography(request.biography());
        }

        if (request.dateOfBirth() != null || request.dateOfDeath() != null) {
            author.updateLifeDates(
                request.dateOfBirth() != null ? request.dateOfBirth() : author.getDateOfBirth(),
                request.dateOfDeath() != null ? request.dateOfDeath() : author.getDateOfDeath()
            );
        }

        // Save updated author
        Author updatedAuthor = authorRepository.save(author);

        log.info("Author updated successfully with ID: {}", id);
        return authorMapper.toResponse(updatedAuthor);
    }
}
