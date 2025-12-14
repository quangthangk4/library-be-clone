package com.library.catalog.application.usecase.tag;

import com.library.catalog.application.dto.response.TagResponse;
import com.library.catalog.application.mapper.TagMapper;
import com.library.catalog.domain.entities.Tag;
import com.library.catalog.domain.repository.TagRepository;
import com.library.catalog.domain.valueobject.TagId;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetTagByIdUseCaseImpl implements GetTagByIdUseCase {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public TagResponse execute(Long id) {
        log.info("Fetching tag with ID: {}", id);

        Tag tag = tagRepository.findById(TagId.of(id))
            .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));

        return tagMapper.toResponse(tag);
    }
}
