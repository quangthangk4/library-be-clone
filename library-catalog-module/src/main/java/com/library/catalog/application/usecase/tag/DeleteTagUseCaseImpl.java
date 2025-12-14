package com.library.catalog.application.usecase.tag;

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
public class DeleteTagUseCaseImpl implements DeleteTagUseCase {

    private final TagRepository tagRepository;

    @Override
    @Transactional
    public void execute(Long id) {
        log.info("Deleting tag with ID: {}", id);

        TagId tagId = TagId.of(id);

        // Check if tag exists
        if (!tagRepository.findById(tagId).isPresent()) {
            throw new AppException(ErrorCode.TAG_NOT_FOUND);
        }

        // Note: In a real system, you might want to check if tag is used by any publications
        // For now, we'll allow deletion (cascade will handle references)

        // Delete tag
        tagRepository.deleteById(tagId);

        log.info("Tag deleted successfully with ID: {}", id);
    }
}
