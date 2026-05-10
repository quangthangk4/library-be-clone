package com.library.catalog.application.impl;

import com.library.catalog.application.SaveDocumentUrlUseCase;
import com.library.catalog.infrastructure.persistence.entity.PublicationEntity;
import com.library.catalog.infrastructure.persistence.repository.PublicationJpaRepository;
import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.shared.port.StoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveDocumentUrlUseCaseImpl implements SaveDocumentUrlUseCase {

    private final PublicationJpaRepository publicationRepository;
    private final StoragePort storagePort;

    @Override
    @Transactional
    public String execute(Long publicationId, String s3Key) {
        PublicationEntity publication = publicationRepository.findById(publicationId)
            .orElseThrow(() -> new AppException(ErrorCode.PUBLICATION_NOT_FOUND));

        String fileUrl = storagePort.buildPublicUrl(s3Key);
        publication.setFileUrl(fileUrl);
        publicationRepository.save(publication);

        log.info("Document URL saved for publication={}: {}", publicationId, fileUrl);
        return fileUrl;
    }
}
