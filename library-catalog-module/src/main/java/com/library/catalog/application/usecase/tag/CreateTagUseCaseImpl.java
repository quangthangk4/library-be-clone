package com.library.catalog.application.usecase.tag;

import com.library.catalog.application.dto.request.CreateTagRequest;
import com.library.catalog.application.dto.response.TagResponse;
import com.library.catalog.application.mapper.TagMapper;
import com.library.catalog.domain.entities.Tag;
import com.library.catalog.domain.repository.TagRepository;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTagUseCaseImpl implements CreateTagUseCase {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public TagResponse execute(CreateTagRequest request) {
        log.info("Creating tag with name: {}", request.tagName());

        // Check if tag name already exists
        if (tagRepository.existsByNameIgnoreCase(request.tagName().trim())) {
            throw new AppException(ErrorCode.TAG_NAME_ALREADY_EXISTS);
        }

        // Create tag entity
        Tag tag = Tag.create(request.tagName());

        // Save tag
        Tag savedTag = tagRepository.save(tag);

        log.info("Tag created successfully with ID: {}", savedTag.getId().getValue());
        return tagMapper.toResponse(savedTag);
    }
}
