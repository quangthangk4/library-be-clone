package com.library.catalog.application.usecase.author;

import com.library.catalog.domain.repository.AuthorRepository;
import com.library.catalog.domain.repository.PublicationRepository;
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
public class DeleteAuthorUseCaseImpl implements DeleteAuthorUseCase {

    private final AuthorRepository authorRepository;
    private final PublicationRepository publicationRepository;

    @Override
    @Transactional
    public void execute(Long id) {
        log.info("Deleting author with ID: {}", id);

        AuthorId authorId = AuthorId.of(id);

        // Check if the author exists
        if (authorRepository.findById(authorId).isEmpty()) {
            throw new AppException(ErrorCode.AUTHOR_NOT_FOUND);
        }

        // Check if the author has publications
        if (!publicationRepository.findByAuthorId(authorId).isEmpty()) {
            throw new AppException(ErrorCode.CANNOT_DELETE_AUTHOR_HAS_PUBLICATIONS);
        }

        // Delete author
        authorRepository.deleteById(authorId);

        log.info("Author deleted successfully with ID: {}", id);
    }
}
