package com.library.catalog.application.usecase.tag;

import com.library.catalog.application.dto.response.TagResponse;
import com.library.catalog.application.mapper.TagMapper;
import com.library.catalog.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllTagsUseCaseImpl implements GetAllTagsUseCase {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> execute() {
        log.info("Fetching all tags");

        return tagRepository.findAll().stream()
            .map(tagMapper::toResponse)
            .collect(Collectors.toList());
    }
}
