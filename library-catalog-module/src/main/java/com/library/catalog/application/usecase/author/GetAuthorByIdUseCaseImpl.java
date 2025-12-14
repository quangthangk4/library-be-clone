package com.library.catalog.application.usecase.author;

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
public class GetAuthorByIdUseCaseImpl implements GetAuthorByIdUseCase {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public AuthorResponse execute(Long id) {
        log.info("Fetching author with ID: {}", id);

        Author author = authorRepository.findById(AuthorId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        return authorMapper.toResponse(author);
    }
}
